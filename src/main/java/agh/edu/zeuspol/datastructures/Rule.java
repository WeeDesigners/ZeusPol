package agh.edu.zeuspol.datastructures;

import agh.edu.zeuspol.generators.IdGenerator;
import java.util.List;
import java.util.Objects;

public abstract class Rule {
  public final long id;
  public final RuleAttribute attribute;
  public final RuleSubject subject;
  public final List<Number> value;
  public final UnitType unit;
  public final RelationType relation;

  public Rule(
      RuleAttribute attribute,
      RuleSubject subject,
      List<Number> value,
      UnitType unit,
      RelationType action) {
    this.id = IdGenerator.getRuleId();
    this.attribute = attribute;
    this.subject = subject;
    this.value = value;
    this.unit = unit;
    this.relation = action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Rule rule = (Rule) o;
    return attribute == rule.attribute
        && subject == rule.subject
        && Objects.equals(value, rule.value)
        && unit == rule.unit
        && relation == rule.relation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(attribute, subject, value, unit, relation);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "{ " + id + ", " + attribute + ", " + subject + ", " + value + ", " + unit + ", "
        + relation + " }";
  }
}
