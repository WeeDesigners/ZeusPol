package agh.edu.zeuspol.datastructures.types.attributes;

public class Action {

  public final String collectionName;
  public final String actionName;
  public final Params params;

  public Action(String collectionName, String actionName, Params params) {
    this.collectionName = collectionName;
    this.actionName = actionName;
    this.params = params;
  }
}
