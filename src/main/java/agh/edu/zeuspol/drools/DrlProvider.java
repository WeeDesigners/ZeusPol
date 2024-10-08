package agh.edu.zeuspol.drools;

import java.util.List;

public class DrlProvider {

    private String testPath = "test/test.drl";

    private String testRule =
            """
            import io.github.hephaestusmetrics.model.metrics.Metric;
//            Add import here
            
            rule "memory-usage-above-20pr"
                when
                    Metric(
                        queryTag == "cluster-memory-usage",
                        value > 20
                    )
                then
                    System.out.println("Processing 'memory-usage-above-70pr'...");
//                    add code here
            end
            """;

    public List<DrlStringFile> getDrlFiles() {
        return List.of(new DrlStringFile(this.testPath, this.testRule));
    }
}
