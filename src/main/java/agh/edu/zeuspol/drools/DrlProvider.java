package agh.edu.zeuspol.drools;

import java.util.List;

public class DrlProvider {

    private String testPath = "test/test.drl";

    private String testRule =
            """
            rule "memory-usage-above-20pr"
                when
                    clusterMemoryUsage : Metric(
                        queryTag == "cluster-memory-usage",
                        clusterMemoryUsageValue : value
                    )
                    eval((clusterMemoryUsageValue > 20))
                then
                    System.out.println("Processing 'memory-usage-above-70pr'...");
            end
            """;

    public List<DrlStringFile> getDrlFiles() {
        return List.of(new DrlStringFile(this.testPath, this.testRule));
    }
}
