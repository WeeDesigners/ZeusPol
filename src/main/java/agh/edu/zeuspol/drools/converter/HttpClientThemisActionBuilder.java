package agh.edu.zeuspol.drools.converter;

import java.util.List;
import java.util.Map;

public class HttpClientThemisActionBuilder extends ThemisActionBuilder{
    private List<String> imports = List.of(
            "import java.net.URI",
            "import java.net.http.HttpClient",
            "import java.net.http.HttpRequest",
            "import java.net.http.HttpResponse",
            "import java.net.http.HttpHeaders",
            "import java.util.Map",
            "import java.util.List"
    );

    @Override
    public String buildThemisAction() {
        return this.logInfoString() + "\n" +
                "String url = \"http://themis-executor.themis-executor:8080/execute\";" + "\n" +
                "String jsonInputString = \"{" +
                "\\\"collectionName\\\": \\\"" + this.getCollectionName() + "\\\"," +
                "\\\"actionName\\\": \\\"" + this.getActionName() + "\\\"," +
                "\\\"params\\\": {" + this.paramsString() + "}}\";" + "\n" +
                "HttpClient client = HttpClient.newHttpClient();" + "\n" +
                "HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(\"Content-Type\", \"application/json\").POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();" + "\n" +
                "try {\n" +
                "HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());\n" +
                "System.out.println(\"Response Code: \" + response.statusCode());\n" +
                "System.out.println(\"Response Body: \" + response.body());\n" +
                "} catch (Exception e) {\n" +
                "e.printStackTrace();\n" +
                "}\n";
    }


    @Override
    public List<String> importsNeeded() {
        return this.imports;
    }

    private String paramsString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : super.getParams().entrySet()) {
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
