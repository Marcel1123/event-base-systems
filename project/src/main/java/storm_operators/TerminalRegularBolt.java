package storm_operators;

import brokers.BrokerRegular;
import brokers.IBroker;
import lombok.SneakyThrows;
import models.Publication;
import models.PublicationAvg;
import models.TPublication;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import utils.Serialization;
import utils.generators.PublicationGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        byte[] publication = (byte[])input.getValueByField("publication");
        TPublication publication1 = Serialization.deserialize(publication);
        Publication publication2 = Publication.builder()
                .direction(publication1.getDirection())
                .city(publication1.getCity())
                .rain(publication1.getRain())
                .wind(publication1.getWind())
                .temp(publication1.getTemp())
                .stationId(publication1.getStationId())
                .data(new Date(Long.parseLong(publication1.getDate())))
                .build();
        broker.process(publication2);
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
