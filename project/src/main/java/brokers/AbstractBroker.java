package brokers;

import lombok.SneakyThrows;
import models.*;
import org.apache.storm.mqtt.MqttLogger;
import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractBroker implements IBroker{
    private final static Logger LOG = LoggerFactory.getLogger(
            BrokerRegular.class);

    /**
     * This is used for listen for incoming subscriptions
     */
    protected final String brokerListenTopic;

    /** The neighbour broker */
    IBroker NB = null;

    private boolean listenLoopFlag;
    private boolean listenThreadStarted;
    /**
     * The default wait in milliseconds.
     */
    protected static final int WAIT_MILLIS_DEFAULT = 500;

    /**
     * Initializes {@code broker} and starts it.
     * @throws Exception if an exception during adding a connector occurs
     */
    public void startBroker() throws Exception
    {
        if (!MqttBrokerService.getInstance().isBrokerStarted())
        {
            MqttBrokerService.getInstance().startBroker();
        }
    }

    /**
     * Initializes {@code connection}.
     * @throws Exception if an exception during connecting to connector occurs
     */
    public synchronized void startListener() throws Exception {
        if (!listenThreadStarted) {
            listenLoopFlag = true;
            listenThreadStarted = true;
            Thread t1 = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("Start listen messages in thread id: " + Thread.currentThread().getId());
                    MQTT client = new MQTT();
                    client.setTracer(new MqttLogger());
                    client.setHost("tcp://localhost:8883");
                    client.setClientId("MqttBrokerListener" + brokerListenTopic);

                    BlockingConnection connection = client.blockingConnection();
                    connection.connect();
                    Topic[] topics = {new Topic(brokerListenTopic, QoS.AT_LEAST_ONCE)};
                    connection.subscribe(topics);
                    while (listenLoopFlag) {
                        //LOG.debug("Here new messages should be parsed" + Thread.currentThread().getId());
                        Message message = connection.receive();
                        System.out.println("Received messaged with topic:" + message.getTopic());
                        String payload = new String(message.getPayload());
                        System.out.println("Payload content: " + payload);
                        message.ack();
                        addSubscriptionFromPayload(payload);
                        // Thread.sleep(500);
                    }
                    connection.disconnect();
                }
            });
            t1.start();
        }
    }

    protected AbstractBroker(final String brokerListenTopic)
    {
        this.brokerListenTopic = brokerListenTopic;
    }


    protected void finalize()
    {
        listenLoopFlag = false;
        listenThreadStarted = false;
        System.out.println("Object is destroyed by the Garbage Collector");
    }

    @Override
    public void setNB(final IBroker nb)
    {
        this.NB = nb;
    }


}
