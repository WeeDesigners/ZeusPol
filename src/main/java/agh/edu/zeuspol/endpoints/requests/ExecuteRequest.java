package agh.edu.zeuspol.endpoints.requests;

import java.util.HashMap;

public class ExecuteRequest {
    private String collectionName;
    private String actionName;
    private HashMap<String, String> params;

    public ExecuteRequest() {
        collectionName = "";
        actionName = "";
        params = new HashMap<>();
    }

    public ExecuteRequest(String collectionName, String actionName, HashMap<String, String> parameters) {
        this.collectionName = collectionName;
        this.actionName = actionName;
        this.params = parameters;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public HashMap<String, String> getParameters() {
        return params;
    }

    public void addParameter(String key, String value) {
        this.params.put(key, value);
    }

}