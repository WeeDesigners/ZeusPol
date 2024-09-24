package agh.edu.zeuspol.endpoints.requests;

import java.util.HashMap;

public class ExecuteRequest {
    private String collectionName;
    private String actionName;
    private HashMap<String, String> params;

    public ExecuteRequest() {
        collectionName = "default_collection_name";
        actionName = "default_action_name";
        params = new HashMap<>();
    }

    public ExecuteRequest(String collectionName, String actionName, HashMap<String, String> params) {
        this.collectionName = collectionName;
        this.actionName = actionName;
        this.params = params;
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

    public HashMap<String, String> getParams() {
        return params;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

}
