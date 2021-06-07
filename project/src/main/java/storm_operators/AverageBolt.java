package storm_operators;

import models.Publication;
import models.PublicationAvg;
import models.TPublication;
import models.TPublicationAVG;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import utils.Constant;
import utils.Serialization;
import utils.generators.PublicationGenerator;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

public class AverageBolt extends BaseRichBolt {
    private OutputCollector collector;
    private HashMap<String, PublicationAvg> city_avg_pub_map;
    private Long tuple_counter;
    private Long tuple_max_count;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        city_avg_pub_map = new HashMap<>();
        tuple_counter = 0L;
        try {
            tuple_max_count = ((Long)map.get(Constant.MaxTuplesAvg));
            System.out.println("AVG_BOLT: Received:" +tuple_max_count+" as tuple_max_count");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println("AVG_BOLT: put 10 as default tuple_max_count");
            tuple_max_count = 10L;
        }
    }

    @Override
    public void execute(Tuple tuple) {
        byte[] publication = (byte[])tuple.getValueByField("publication");
        TPublication pub = Serialization.deserialize(publication);
        Publication publication2 = Publication.builder()
                .direction(pub.getDirection())
                .city(pub.getCity())
                .rain(pub.getRain())
                .wind(pub.getWind())
                .temp(pub.getTemp())
                .stationId(pub.getStationId())
                .data(new Date(Long.parseLong(pub.getDate())))
                .build();
        // System.out.println("Bolt entered");
        // System.out.println(pub);
        tuple_counter += 1;
        if(tuple_counter < tuple_max_count)
        {
            PublicationAvg avg_pub_to_put = city_avg_pub_map.getOrDefault(pub.getCity(), new PublicationAvg(pub.getCity()));
            avg_pub_to_put.addPublication(publication2);
            city_avg_pub_map.put(pub.getCity(),avg_pub_to_put);
            //System.out.println("AVG BOLT Put: " + city_avg_pub_map);
        }
        else
        {
            // System.out.println("AVG BOLT emit ");
            for (PublicationAvg pub_avg : city_avg_pub_map.values())
            {
                // System.out.println("AVG BOLT emit: " + pub_avg);
                TPublicationAVG tPublicationAVG = new TPublicationAVG(pub_avg);
                this.collector.emit(new Values((Object) Serialization.serialize(tPublicationAVG)));
            }
            city_avg_pub_map.clear();
            tuple_counter = 0L;
        }
        // System.out.println("Bolt finished");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("publication_avg"));
    }
}
