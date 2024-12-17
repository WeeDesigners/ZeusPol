package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurlThemisActionBuilder extends ThemisActionBuilder {

  @Override
  public String buildThemisAction(Action action) {
    return "try {\n"
        + "Process process = Runtime.getRuntime().exec(new String[]{\"bash\", \"-c\", "
        + "\""
        + this.createHttpRequest(action)
        + "\"});\n"
        + "}"
        + "catch (Exception e) {e.printStackTrace();}\n";
  }

  @Override
  public List<String> importsNeeded() {
    return new ArrayList<>();
  }

  //    Helper methods
  private String createHttpRequest(Action action) {
    StringBuilder sb = new StringBuilder();
    sb.append("curl -X POST http://themis-executor.themis-executor:8080/execute ")
        .append("-H \\\"Content-Type: application/json\\\" ")
        .append("-d '{\\\"collectionName\\\": \\\"")
        .append(action.collectionName)
        .append("\\\",")
        .append("\\\"actionName\\\": \\\"")
        .append(action.actionName)
        .append("\\\",")
        .append("\\\"params\\\": {");

    // Append parameters
    for (Map.Entry<String, String> entry : action.params.entrySet()) {
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
