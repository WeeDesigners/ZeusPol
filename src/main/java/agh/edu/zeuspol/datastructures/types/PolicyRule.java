package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PolicyRule {

  public long id;
  public String name;
  private List<Condition> conditions;
  public Action action;
  public int maxRetry = 3;
  public long cooldownSec = 60;

  public PolicyRule() {}

  public PolicyRule(long id, String name, Action action) {
    this.id = id;
    this.name = name;
    this.conditions = new ArrayList<>();
    this.action = action;
  }

  public PolicyRule(long id, String name, Action action, int maxRetry, int cooldownSec) {
    this.id = id;
    this.name = name;
    this.conditions = new ArrayList<>();
    this.action = action;
    this.maxRetry = maxRetry;
    this.cooldownSec = cooldownSec;
  }

  public boolean addConditions(List<Condition> conditions) {
    for (Condition condition : conditions) {
      if (!this.conditions.contains(condition)) {
        return false;
      }
    }
    return true;
  }

  public boolean addCondition(Condition condition) {
    return conditions.add(condition);
  }

  public List<Condition> getConditions() {
    return new ArrayList<>(conditions);
  }

  public void clearConditions() {
    this.conditions.clear();
  }

  @Override
  public boolean equals(Object o) {
    PolicyRule rule = (PolicyRule) o;
    return super.equals(o)
        && this.id == rule.id
        && this.name.equals(rule.name)
        && this.conditions.equals(rule.getConditions())
        && this.action.equals(rule.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, conditions, action);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "{ " + id + ", " + name + ", " + conditions + ", " + action + " }";
  }
}
