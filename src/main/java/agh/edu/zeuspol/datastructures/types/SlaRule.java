package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;
import java.util.ArrayList;
import java.util.List;

public class SlaRule {

  public final long id;
  public ValueType valueType;
  public List<Condition> conditions;

  public SlaRule(long id, ValueType valueType) {
    this.id = id;
    this.valueType = valueType;
    this.conditions = new ArrayList<>();
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
  public String toString() {
    return "{" + id + ", " + valueType + ", " + conditions + "}";
  }
}
