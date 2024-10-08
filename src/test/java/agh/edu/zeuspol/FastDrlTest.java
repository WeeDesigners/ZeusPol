package agh.edu.zeuspol;

import agh.edu.zeuspol.drools.DrlProvider;
import agh.edu.zeuspol.drools.DrlRuleExecutor;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import io.github.hephaestusmetrics.model.ResultType;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;


public class FastDrlTest {

    @Test
    public void test() {
        DrlProvider provider = new DrlProvider();
        DynamicDrlBuilder builder = new DynamicDrlBuilder();
        for (DrlStringFile f: provider.getDrlFiles()) {
            builder.addFile(f.getPath(), f.getFileContent());
        }

        DrlRuleExecutor executor = builder.build();


        Metric m = new Metric("cluster-memory-usage", ResultType.SCALAR ,new HashMap<String, String>(), 10, "60.1");
        executor.fireRules(List.of(m));
    }

    @Test
    public void test2() {
        DynamicDrlBuilder builder = new DynamicDrlBuilder();

        String drlRule = """
            import java.util.List;
            import java.util.ArrayList;
            
            rule "memory-usage-above-20pr"
                when
                then
                    List<String> l = new ArrayList<String>();
                    l.add("memory-usage-above-20pr");
                    l.add("beka");
                    System.out.println(l);
            end
            """;

        builder.addFile("temp/temp.drl", drlRule);
        DrlRuleExecutor executor = builder.build();


        executor.fireRules(null);
    }
}
