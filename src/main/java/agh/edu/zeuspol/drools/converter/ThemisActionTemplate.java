package agh.edu.zeuspol.drools.converter;

public class ThemisActionTemplate {
  private final String collectionName;
  private final String actionName;
  private final String[] optionalParams;
  private final String[] requiredParams;

  public ThemisActionTemplate(
      String collectionName, String actionName, String[] optionalParams, String[] requiredParams) {
    this.collectionName = collectionName;
    this.actionName = actionName;
    this.optionalParams = optionalParams;
    this.requiredParams = requiredParams;
  }

  public String getActionName() {
    return actionName;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public String[] getOptionalParams() {
    return optionalParams;
  }

  public String[] getRequiredParams() {
    return requiredParams;
  }
}
