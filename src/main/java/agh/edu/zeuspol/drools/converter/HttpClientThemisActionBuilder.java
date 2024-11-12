package agh.edu.zeuspol.drools.converter;

import java.util.List;

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
                "String jsonInputString = \"{\\\"collectionName\\\": \\\"kubernetes\\\",\\\"actionName\\\": \\\"ChangeResourcesOfContainerWithinDeploymentAction\\\",\\\"params\\\": {\\\"containerName\\\": \\\"test-app\\\",\\\"limitsCpu\\\": \\\"2\\\",\\\"namespace\\\": \\\"test-app\\\",\\\"deploymentName\\\": \\\"test-app\\\",\\\"limitsMemory\\\": \\\"800Mi\\\",\\\"requestsMemory\\\": \\\"800Mi\\\",\\\"requestsCpu\\\": \\\"2\\\",\\\"collectionName\\\": \\\"kubernetes\\\",\\\"actionName\\\": \\\"ChangeResourcesOfContainerWithinDeploymentAction\\\"}}\";" + "\n" +
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
}
