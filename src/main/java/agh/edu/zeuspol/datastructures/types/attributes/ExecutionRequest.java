package agh.edu.zeuspol.datastructures.types.attributes;


public class ExecutionRequest {
  private String collectionName;
  private String actionName;
  private Params params;

  public ExecutionRequest(String collectionName, String actionName) {
    this.collectionName = collectionName;
    this.actionName = actionName;
    params = new Params();
  }

  public ExecutionRequest(String collectionName, String actionName, Params params) {
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

  public Params getParams() {
    return params;
  }

  public void addParam(String key, String value) {
    this.params.put(key, value);
  }
}
