import brokers.BrokerAverage;
import brokers.BrokerRegular;
import brokers.IBroker;
import lombok.SneakyThrows;
import models.Publication;
import models.TPublication;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import storm_operators.AverageBolt;
import storm_operators.SourcePublicationSpout;
import storm_operators.TerminalAverageBolt;
import storm_operators.TerminalRegularBolt;
import utils.generators.PublicationGenerator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Constant.*;

public class App {
    @SneakyThrows
    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();
        SourcePublicationSpout spout = new SourcePublicationSpout();
        AverageBolt avg_blot = new AverageBolt();

        IBroker avg_broker = new BrokerAverage(AvgBrokerListenTopic);
        IBroker reg_broker = new BrokerRegular(RegBrokerListenTopic);
        avg_broker.setNB(reg_broker);
        reg_broker.setNB(avg_broker);

        TerminalAverageBolt terminal_avg_bolt = new TerminalAverageBolt(avg_broker);
        TerminalRegularBolt terminal_reg_bolt = new TerminalRegularBolt(reg_broker);


        builder.setSpout(SPOUT_ID, spout, 2);
    	builder.setBolt(AVG_BOLT_ID, avg_blot).shuffleGrouping(SPOUT_ID);
        builder.setBolt(TERMINAL_AVG_BOLT_ID, terminal_avg_bolt).shuffleGrouping(AVG_BOLT_ID);
        builder.setBolt(TERMINAL_REG_BOLT_ID, terminal_reg_bolt).shuffleGrouping(SPOUT_ID);

        Config config = new Config();
        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        // config.put(Config.TOPOLOGY_TRANSFER_BATCH_SIZE,1);
        config.put(Config.TOPOLOGY_DISRUPTOR_BATCH_SIZE,1);
        // Integer max_tuple_window_size = 10;
        config.put(MaxTuplesAvg, 10);

        cluster.submitTopology("weather_topology", config, topology);

        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("weather_topology");
        cluster.shutdown();
        // cluster.close();



//        TPublication publication1 = new TPublication();
//        Publication publication = PublicationGenerator.createNewPublication();
//
//        TPublication publication1 = new TPublication(publication);
//
//        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
//        TDeserializer deserializer = new TDeserializer(new TBinaryProtocol.Factory());
//        byte[] serialized = serializer.serialize(publication1);
//        TPublication deserialized = new TPublication();
//
//        deserializer.deserialize(deserialized, serialized);
//
//        assertEquals(publication, deserialized);
    }
}