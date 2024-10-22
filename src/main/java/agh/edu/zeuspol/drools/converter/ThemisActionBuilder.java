package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import java.util.List;

public abstract class ThemisActionBuilder {

  //    TODO - consider removing collectionName and actionName
  private String collectionName;
  private String actionName;
  private final Params params = new Params();
  private Action action;

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
    this.addParam("collectionName", collectionName);
  }

  public void setActionName(String actionName) {
    this.actionName = actionName;
    this.addParam("actionName", actionName);
  }

  public void setAction(Action action) {
    this.action = action;
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

  //    TODO - consider a copy of params
  public Params getParams() {
    return this.params;
  }

  public Action getAction() {
    return action;
  }

  public abstract String buildThemisAction();

  public abstract List<String> importsNeeded();

  protected void checkRequestValidity() {
    try {
      ThemisActionValidator.validate(this.action, this.getParams());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  protected void propertiesNullCheck() throws NullPointerException {
    if (this.action == null) {
      throw new NullPointerException("Action not set");
    } else if (this.collectionName == null) {
      throw new NullPointerException("Collection name not set");
    } else if (this.actionName == null) {
      throw new NullPointerException("Action name not set");
    }
  }
}
