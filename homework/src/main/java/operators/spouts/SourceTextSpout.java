package operators.spouts;

import models.Publication;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import util.PublicationGenerator;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.Parameter.NUMBER_OF_MESSAGES;

public class SourceTextSpout extends BaseRichSpout {

    private static final long serialVersionUID = 1;

    private SpoutOutputCollector collector;

    private int i = 0;
    private String task;

    private int globalcounter = 0;
    private List<Integer> tasks;

    private int id = 0;
    private HashMap<Integer,Values> tobeconfirmed;

    // add template <String, Object> to conf declaration for Storm 2
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
        this.task = context.getThisComponentId()+" "+context.getThisTaskId();
        System.out.println("----- Started spout task: "+this.task);
        this.tasks = context.getComponentTasks("terminal_bolt");

        this.tobeconfirmed = new HashMap<Integer, Values>();

    }

    public void nextTuple() {
        if(i < NUMBER_OF_MESSAGES) {
            Publication publication = PublicationGenerator.createNewPublication();
            this.collector.emit(new Values(publication.toString()), id);
            this.tobeconfirmed.put(id, new Values(publication.toString()));
            id++;
            i++;
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("words"));
//        declarer.declareStream("secondary", true, new Fields("globalcount"));
    }

    public void ack(Object id) {
        System.out.println("----- ACKED detected at "+ this.task +" for "+id.toString()+this.tobeconfirmed.get(id).toString());
        this.tobeconfirmed.remove(id);
    }

    public void fail(Object id) {
        System.out.println("----- FAILED detected at "+ this.task +" - re-emitting "+id.toString()+this.tobeconfirmed.get(id).toString());
        this.collector.emit(this.tobeconfirmed.get(id), id);
    }
}
