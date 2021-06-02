package storm_operators;

import brokers.BrokerRegular;
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

public class TerminalRegularBolt extends BaseRichBolt {
    private OutputCollector collector;
    private IBroker broker;

    @SneakyThrows
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector)
    {
        this.collector = outputCollector;
        broker.startBroker();
        broker.startPublisher();
        broker.startListener();
    }

    @Override
    public void execute(Tuple input)
    {
        //System.out.println("TerminalRegularBolt entered received input from: " +input.getSourceStreamId());
        Publication publication = (Publication)input.getValueByField("publication");
        broker.process(publication);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
    {
    }

    public void cleanup() {
        System.out.println("Topology Result:");
    }
    public TerminalRegularBolt(IBroker broker)
    {
        this.broker = broker;
    }
}
