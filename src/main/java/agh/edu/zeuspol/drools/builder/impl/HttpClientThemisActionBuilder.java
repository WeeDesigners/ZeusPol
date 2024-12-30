package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import java.util.List;
import java.util.Map;

public class HttpClientThemisActionBuilder extends DrlActionBuilder {

  private Action action;

  public HttpClientThemisActionBuilder(Action action) {
    this.action = action;
  }

  private final List<String> imports =
      List.of(
          "import java.net.URI",
          "import java.net.http.HttpClient",
          "import java.net.http.HttpRequest",
          "import java.net.http.HttpResponse",
          "import java.net.http.HttpHeaders",
          "import java.util.Map",
          "import java.util.List");

  @Override
  public String build() {
    return "System.out.println(\"Rule "
        + action.collectionName
        + action.actionName
        + " fired\");\n" // TODO - to be deleted, logging should not be added here, add it somewhere
        // else
        + "\n"
        + "String url = \"http://themis-executor.themis-executor:8080/execute\";"
        + "\n"
        + "String jsonInputString = \"{"
        + "\\\"collectionName\\\": \\\""
        + action.collectionName
        + "\\\","
        + "\\\"actionName\\\": \\\""
        + action.actionName
        + "\\\","
        + "\\\"params\\\": {"
        + this.paramsString(action.params)
        + "}}\";\n"
        + "HttpClient client = HttpClient.newHttpClient();\n"
        + "HttpRequest request ="
        + " HttpRequest.newBuilder().uri(URI.create(url)).header(\"Content-Type\","
        + " \"application/json\").POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();\n"
        + "try {\n"
        + "HttpResponse<String> response = client.send(request,"
        + " HttpResponse.BodyHandlers.ofString());\n"
        + "System.out.println(\"Response Code: \" + response.statusCode());\n"
        + "System.out.println(\"Response Body: \" + response.body());\n"
        + "} catch (Exception e) {\n"
        + "e.printStackTrace();\n"
        + "}\n";
  }

  @Override
  public List<String> imports() {
    return this.imports;
  }

  private String paramsString(Params params) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      sb.append("\\\"")
          .append(entry.getKey())
          .append("\\\": \\\"")
          .append(entry.getValue())
          .append("\\\",");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }
}
