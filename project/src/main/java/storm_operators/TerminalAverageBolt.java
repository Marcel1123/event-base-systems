package storm_operators;

import brokers.IBroker;
import lombok.SneakyThrows;
import models.Publication;
import models.PublicationAvg;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class TerminalAverageBolt extends BaseRichBolt {
    private OutputCollector collector;
    private IBroker broker;

    @SneakyThrows
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        broker.startBroker();
        broker.startPublisher();
        broker.startListener();
    }

    @Override
    public void execute(Tuple input) {
        // System.out.println("Terminal entered");
        // System.out.println("TerminalAverageBolt entered received input from: " + input.getSourceStreamId());
        PublicationAvg avg_pub = (PublicationAvg)input.getValueByField("publication_avg");
        this.broker.process(avg_pub);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
    {
    }

    public void cleanup() {
        System.out.println("Topology Result:");
    }

    public TerminalAverageBolt(final IBroker broker)
    {
        this.broker = broker;
    }
}