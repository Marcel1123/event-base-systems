package storm_operators;

import models.Publication;
import models.PublicationAvg;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class TerminalBolt extends BaseRichBolt {
    private OutputCollector collector;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(Tuple input) {
        // System.out.println("Terminal entered");
        if (input.getSourceStreamId().equals("secondary"))
        {
            // here the simple matching alg
            Publication publication = (Publication)input.getValueByField("publication");
            System.out.println("Terminal: "+ publication);
        }
        else
        {
            PublicationAvg avg_pub = (PublicationAvg)input.getValueByField("publication_avg");
            // System.out.println("Terminal: "+ avg_pub);
            // here complex matching
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }

    public void cleanup() {
        System.out.println("Topology Result:");
        /*
        for (Map.Entry<String, Integer> entry : this.count.entrySet()) {

            System.out.println(entry.getKey()+" - "+entry.getValue());
        }
        */

    }
}
