import operators.spouts.SourceTextSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import util.PublicationGenerator;

public class App {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        SourceTextSpout spout = new SourceTextSpout();

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        config.put(Config.TOPOLOGY_DISRUPTOR_BATCH_SIZE,1);

        cluster.submitTopology("count_topology", config, topology);

        cluster.killTopology("count_topology");
        cluster.shutdown();
    }
}
