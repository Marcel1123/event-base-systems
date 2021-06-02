package brokers;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttBrokerService {
    private static MqttBrokerService single_instance = null;
    private static BrokerService broker;
    private final static Logger LOG = LoggerFactory.getLogger(
            MqttBrokerService.class);
    private static boolean isStarted;

    private MqttBrokerService()
    {
        isStarted = false;
        broker = null;
    }

    // static method to create instance of Singleton class
    public static MqttBrokerService getInstance()
    {
        if (single_instance == null)
            single_instance = new MqttBrokerService();

        return single_instance;
    }

    /**
     * Initializes {@code broker} and starts it.
     * @throws Exception if an exception during adding a connector occurs
     */
    public synchronized static void startBroker() throws Exception {
        if (!isStarted) {
            LOG.info("Starting broker..." + Thread.currentThread().getId());
            broker = new BrokerService();
            broker.addConnector("mqtt://localhost:8883");
            broker.setDataDirectory("target");
            broker.start();
            LOG.info("MQTT broker started");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        LOG.info("Shutting down MQTT broker...");
                        broker.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            isStarted = true;
        }
    }

    public synchronized boolean isBrokerStarted()
    {
        return isStarted;
    }
}
