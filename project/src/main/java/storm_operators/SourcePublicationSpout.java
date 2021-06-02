package storm_operators;

import lombok.SneakyThrows;
import models.Publication;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import utils.Constant;
import utils.generators.PublicationGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourcePublicationSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private String task;
    private List tasks;

    // pentru retrimitere in caz de pierderi de tuple
    private int id = 0;
    private HashMap<Integer,Values> tobeconfirmed;


    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.task = topologyContext.getThisComponentId()+" "+topologyContext.getThisTaskId();
        System.out.println("----- Started spout task: "+ this.task);

        //this.tasks = topologyContext.getComponentTasks(Constant.TERMINAL_BOLT_ID);
    }

    @SneakyThrows
    @Override
    public void nextTuple() {
        Publication publication = PublicationGenerator.createNewPublication();
        //Publication global_publication = publication;
        // System.out.println(publication);
        this.collector.emit(new Values(publication));
        //this.collector.emitDirect((int)tasks.get(0), "secondary", new Values(publication));
        // Thread.sleep(50);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("publication"));
        // outputFieldsDeclarer.declareStream("secondary", true, new Fields("publication"));
    }

}
