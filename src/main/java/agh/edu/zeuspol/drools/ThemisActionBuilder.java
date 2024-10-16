package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.exceptions.NoThemisActionTemplateFoundException;

import java.util.Map;

public class ThemisActionBuilder {

    private String collectionName;
    private String actionName;
    private Params params = new Params();
    private Action action;

    public ThemisActionBuilder(Action action) {
        this.action = action;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void addParams(Params params) {
        for (String key : params.keySet()) {
            this.addParam(key, params.get(key));
        }
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getActionName() {
        return actionName;
    }

    public Params getParams() {
        return params;
    }


    public String createHttpRequest() {
        this.checkRequestValidity();

        StringBuilder sb = new StringBuilder();
        sb.append("curl -X POST http://themis-executor.themis-executor:8080/execute ")
                .append("-H \\\"Content-Type: application/json\\\" ")
                .append("-d '{\\\"collectionName\\\": \\\"").append(collectionName).append("\\\",")
                .append("\\\"actionName\\\": \\\"").append(actionName).append("\\\",")
                .append("\\\"params\\\": {");

        // Append parameters
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("\\\"").append(entry.getKey()).append("\\\": \\\"")
                    .append(entry.getValue()).append("\\\",");
        }

        // Remove the last comma
        sb.setLength(sb.length() - 1);

        // Close JSON and the string
        sb.append("}}'");

        return sb.toString();
    }

    public String buildThemisAction() {
        StringBuilder sb = new StringBuilder();
        sb.append("Process process = Runtime.getRuntime().exec(new String[]{\"bash\", \"-c\", ")
                .append("\"")
                .append(this.createHttpRequest())
                .append("\"});\n");
        return sb.toString();
    }


    private boolean checkRequestValidity() {
        try {
            return ThemisActionValidator.validate(this.action, this);
        } catch (NoThemisActionTemplateFoundException e) {
            System.out.println("Action does not have a template! Can't validate request!");
        }
        return false;
    }
}
