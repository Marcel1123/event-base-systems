package brokers;

import lombok.SneakyThrows;
import models.*;
import org.apache.storm.mqtt.MqttLogger;
import org.apache.storm.shade.org.eclipse.jetty.util.ConcurrentHashSet;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class BrokerAverage extends AbstractBroker{

    /**
     * This is used for send/forward new notifications/publications
     */
    private static BlockingConnection connection;

    /** The routing table of each subscription */
    static protected Map<Subscription, Set<String>> TB;

    /** The neighbour broker */
    static protected Set<Subscription> NBSubs = null;
    /** This is the queue where the NB will place the publications to pe processd*/
    static Queue<Publication> NbPublicationToPublish;
    public BrokerAverage(String brokerListenTopic) {
        super(brokerListenTopic);
        NBSubs = new ConcurrentHashSet<>();
        NbPublicationToPublish = new LinkedBlockingDeque<>();
        this.TB = new ConcurrentHashMap<>();

        Thread t1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public synchronized void run() {
                while (true)
                {
                    if(!NbPublicationToPublish.isEmpty())
                    {
                        Publication pub = NbPublicationToPublish.peek();
                        for (Subscription s:
                                getTBKeys()) {
                            if(s.match(pub))
                            {
                                for (String topic:
                                        getTBAtKey(s)) {
                                    publish(topic, pub);
                                }
                            }
                        }
                        NbPublicationToPublish.remove();
                    }
                    else
                    {
                        Thread.sleep(WAIT_MILLIS_DEFAULT);
                    }
                }
            }
        });
        t1.start();
    }

    @Override
    public synchronized void process(Publication pub)
    {
        NbPublicationToPublish.add(pub);
    }

    @SneakyThrows
    @Override
    public synchronized void process(PublicationAvg pub_avg) {
        for (Subscription s:
                getTBKeys()) {
            if(s.match(pub_avg))
            {
                for (String topic:
                        getTBAtKey(s)) {
                    publish(topic, pub_avg);
                }
            }
        }
        for (Subscription s: NBSubs)
        {
            if(s.match(pub_avg))
            {
                System.out.println("Forward:" + pub_avg +"\t reason: " + s);
                forward(pub_avg);
            }

        }
    }

    @Override
    public void forward(Publication pub)
    {
        // should never reach
    }

    @Override
    public void forward(PublicationAvg pub_avg)
    {
        NB.process(pub_avg);
    }

    @Override
    public synchronized void addSubscriptionFromPayload(final String payload)
    {
        String patternString1 = "client_id\\s*:\\s*([\\w|_|\\d]+?)\\s*?,\\s*?subscription\\s*:\\s*?(\\{.+\\})";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(payload);

        if(matcher.find()) {
            String client_id = matcher.group(1);
            String subscription_str = matcher.group(2);
            Subscription sub;
            if (subscription_str.contains("avg_"))
            {
                sub = new ComplexSubscription(subscription_str);
            }
            else
            {
                sub = new SimpleSubscription(subscription_str);
                NB.addSubscriptionFromNB(sub);
            }
            Set<String> new_row = getTBAtKeyOrDefault(sub, new HashSet<String>());
            new_row.add("publication/"+client_id);
            TB.put(sub, new_row);
        }
    }

    @Override
    public synchronized void addSubscriptionFromNB(final Subscription subscription)
    {
        System.out.println("Added subscription from nb: " + subscription);
        this.NBSubs.add(subscription);
    }



    /**
     * Initializes {@code connection}.
     * @throws Exception if an exception during connecting to connector occurs
     */
    public void startPublisher() throws Exception {
        MQTT client = new MQTT();
        client.setTracer(new MqttLogger());
        client.setHost("tcp://localhost:8883");
        client.setClientId("MqttBrokerPublisher"+ brokerListenTopic);
        connection = client.blockingConnection();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Shutting down MQTT client...");
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        connection.connect();
    }

    /**
     * Publishes topics on connection.
     * @throws Exception if an exception during publishing occurs
     */
    protected synchronized void publish(String topic, Publication publication) throws Exception {
        System.out.println("Publishing to topic:"+ topic);
        String payload = publication.toString();

        connection.publish(topic,
                payload.getBytes(),
                QoS.AT_LEAST_ONCE,
                false);
    }

    /**
     * Publishes topics on connection.
     * @throws Exception if an exception during publishing occurs
     */
    protected synchronized void publish(String topic, PublicationAvg publication_avg) throws Exception {
        System.out.println("Publishing to topic: "+ topic);
        String payload = "(" + publication_avg.toString() +")= true)";

        connection.publish(topic,
                payload.getBytes(),
                QoS.AT_LEAST_ONCE,
                false);

    }

    private synchronized Collection<Subscription> getTBKeys()
    {
        return TB.keySet();
    }

    private synchronized Set<String> getTBAtKey(Subscription s)
    {
        return TB.get(s);
    }
    private synchronized Set<String> getTBAtKeyOrDefault(Subscription s, final Set<String> def)
    {
        return TB.getOrDefault(s, def);
    }
    private synchronized Set<String> put(Subscription s, final Set<String> new_row)
    {
        return TB.put(s, new_row);
    }
}
