package operators.bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SplitTextBolt extends BaseRichBolt {

    private static final long serialVersionUID = 2;

    private OutputCollector collector;
    private String task;
    private int task_id;
    private int processed_tuples = 0;

    // exemplu grupare custom
    private int junk_processing_task;

    // add template <String, Object> to conf declaration for Storm 2
    public void prepare(Map topoConf, TopologyContext context, OutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
        this.task_id = context.getThisTaskId();
        this.task = context.getThisComponentId()+" "+this.task_id;
        System.out.println("----- Started task: "+this.task);

        // exemplu grupare custom
        this.junk_processing_task = context.getComponentTasks(context.getThisComponentId()).get(0);
    }

    public void execute(Tuple input) {
        // TODO Auto-generated method stub
        String sourcetext = input.getStringByField("words");

        processed_tuples++;
        System.out.println("----- "+ this.task +
                " received "+ sourcetext +
                " from "+ input.getSourceComponent() + " " + input.getSourceTask() +
                " processed "+ processed_tuples);

        // exemplu grupare custom - filtrare text
        if (task_id == junk_processing_task) return;

        // exemplu functionare obisnuita
        String[] words = sourcetext.split(new String("),("));
        for(String word : words) {
            this.collector.emit(new Values(word));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("word"));
    }
}
