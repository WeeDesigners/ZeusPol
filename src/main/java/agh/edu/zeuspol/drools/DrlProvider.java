package agh.edu.zeuspol.drools;

import java.util.List;

public class DrlProvider {

    private String testPath = "test/test.drl";

    private String testRule =
            """
            import io.github.hephaestusmetrics.model.metrics.Metric;
            import java.io.BufferedReader;
            import java.io.InputStreamReader;
            
            rule "memory-usage-above-20prv  "
                when
                    Metric(
                        queryTag == "cluster-memory-usage",
                        value > 20
                    )
                then
                    System.out.println("Processing 'memory-usage-above-70pr'...");
            end
            rule "cpu-usage-above-60"
                when
                    Metric(
                        queryTag == "test-app_cpu_usage",
                        value > 60
                    )
                then
                    String curlCommand = "curl -X POST http://themis-executor.themis-executor/execute " +
                            "-H \\"Content-Type: application/json\\" " +
                            "-d '{\\"collectionName\\": \\"kubernetes\\"," +
                            "\\"actionName\\": \\"ChangeResourcesOfContainerWithinDeploymentAction\\"," +
                            "\\"params\\": {\\"namespace\\": \\"test-app\\"," +
                            "\\"deploymentName\\": \\"test-app\\"," +
                            "\\"containerName\\": \\"test-app\\"," +
                            "\\"limitsCpu\\": \\"2\\"," +
                            "\\"limitsMemory\\": \\"800Mi\\"," +
                            "\\"requestsCpu\\": \\"2\\"," +
                            "\\"requestsMemory\\": \\"800Mi\\"}}'";
    
                    try {
                      ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", curlCommand);
                      processBuilder.redirectErrorStream(true); // Capture both stdout and stderr
                      Process process = processBuilder.start();

                      // Read the output of the command
                      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                      String line;
                      while ((line = reader.readLine()) != null) {
                          System.out.println(line);
                      }

                      // Wait for the process to finish and get the exit code
                      int exitCode = process.waitFor();
                      System.out.println("Exited with code: " + exitCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            end
            """;

    public List<DrlStringFile> getDrlFiles() {
        return List.of(new DrlStringFile(this.testPath, this.testRule));
    }
}
