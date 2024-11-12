package agh.edu.zeuspol.drools.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurlThemisActionBuilder extends ThemisActionBuilder {

  @Override
  public String buildThemisAction() {

    return this.logInfoString()
        + "try {\n"
        + "Process process = Runtime.getRuntime().exec(new String[]{\"bash\", \"-c\", "
        + "\""
        + this.createHttpRequest()
        + "\"});\n"
        + "}"
        + "catch (Exception e) {e.printStackTrace();}\n";
  }

  @Override
  public List<String> importsNeeded() {
    return new ArrayList<>();
  }

  //    Helper methods
  private String createHttpRequest() {
    super.propertiesNullCheck();
    super.checkRequestValidity();

    StringBuilder sb = new StringBuilder();
    sb.append("curl -X POST http://themis-executor.themis-executor:8080/execute ")
        .append("-H \\\"Content-Type: application/json\\\" ")
        .append("-d '{\\\"collectionName\\\": \\\"")
        .append(super.getCollectionName())
        .append("\\\",")
        .append("\\\"actionName\\\": \\\"")
        .append(super.getActionName())
        .append("\\\",")
        .append("\\\"params\\\": {");

    // Append parameters
    for (Map.Entry<String, String> entry : super.getParams().entrySet()) {
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
