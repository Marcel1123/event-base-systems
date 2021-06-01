import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import storm_operators.AverageBolt;
import storm_operators.SourcePublicationSpout;
import storm_operators.TerminalBolt;

import static utils.Constant.*;

public class App {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        SourcePublicationSpout spout = new SourcePublicationSpout();
        AverageBolt avg_blot = new AverageBolt();
        TerminalBolt terminalbolt = new TerminalBolt();

//    	builder.setSpout(SPOUT_ID, spout);
        builder.setSpout(SPOUT_ID, spout, 2);

    	builder.setBolt(AVG_BOLT_ID, avg_blot).shuffleGrouping(SPOUT_ID);
//    	builder.setBolt(PARSER_BOLT_ID, parserbolt, 2).setNumTasks(4).shuffleGrouping(SPOUT_ID);
//    	builder.setBolt(PARSER_BOLT_ID, parserbolt, 2).setNumTasks(4).allGrouping(SPOUT_ID);
//    	builder.setBolt(PARSER_BOLT_ID, parserbolt, 2).setNumTasks(4).customGrouping(SPOUT_ID, new MyGrouping());

//    	builder.setBolt(COUNT_BOLT_ID, countbolt).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));
        //builder.setBolt(COUNT_BOLT_ID, countbolt, 4).fieldsGrouping(SPLIT_BOLT_ID, new Fields("word"));

        builder.setBolt(TERMINAL_BOLT_ID, terminalbolt).globalGrouping(AVG_BOLT_ID).directGrouping(SPOUT_ID, "secondary"); ;

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();

        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,1024);
        // config.put(Config.TOPOLOGY_TRANSFER_BATCH_SIZE,1);
        config.put(Config.TOPOLOGY_DISRUPTOR_BATCH_SIZE,1);
        Integer max_tuple_window_size = 10;
        config.put(MaxTuplesAvg, max_tuple_window_size);

        cluster.submitTopology("weather_topology", config, topology);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("weather_topology");
        cluster.shutdown();
        // cluster.close();
    }
}