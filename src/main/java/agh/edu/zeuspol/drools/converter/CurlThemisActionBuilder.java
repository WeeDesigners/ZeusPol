package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurlThemisActionBuilder extends ThemisActionBuilder {

  @Override
  public String buildThemisAction(ExecutionRequest executionRequest) {
    return "try {\n"
        + "Process process = Runtime.getRuntime().exec(new String[]{\"bash\", \"-c\", "
        + "\""
        + this.createHttpRequest(executionRequest)
        + "\"});\n"
        + "}"
        + "catch (Exception e) {e.printStackTrace();}\n";
  }

  @Override
  public List<String> importsNeeded() {
    return new ArrayList<>();
  }

  //    Helper methods
  private String createHttpRequest(ExecutionRequest executionRequest) {
    StringBuilder sb = new StringBuilder();
    sb.append("curl -X POST http://themis-executor.themis-executor:8080/execute ")
        .append("-H \\\"Content-Type: application/json\\\" ")
        .append("-d '{\\\"collectionName\\\": \\\"")
        .append(executionRequest.getCollectionName())
        .append("\\\",")
        .append("\\\"actionName\\\": \\\"")
        .append(executionRequest.getActionName())
        .append("\\\",")
        .append("\\\"params\\\": {");

    // Append parameters
    for (Map.Entry<String, String> entry : executionRequest.getParams().entrySet()) {
      sb.append("\\\"")
          .append(entry.getKey())
          .append("\\\": \\\"")
          .append(entry.getValue())
          .append("\\\",");
    }

    // Remove the last comma
    sb.setLength(sb.length() - 1);

    // Close JSON and the string
    sb.append("}}'");

    return sb.toString();
  }
}
