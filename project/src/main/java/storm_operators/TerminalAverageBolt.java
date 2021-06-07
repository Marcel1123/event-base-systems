package storm_operators;

import brokers.IBroker;
import lombok.SneakyThrows;
import models.Publication;
import models.PublicationAvg;
import models.TPublicationAVG;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import utils.Serialization;

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
        byte[] avg_pub = (byte[]) input.getValueByField("publication_avg");
        TPublicationAVG tPublicationAVG = Serialization.deserializeAVG(avg_pub);
        PublicationAvg publicationAvg = new PublicationAvg(tPublicationAVG);
        this.broker.process(publicationAvg);
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
