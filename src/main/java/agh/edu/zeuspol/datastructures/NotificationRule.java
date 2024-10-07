package agh.edu.zeuspol.datastructures;

import java.util.List;
import java.util.Objects;

public class NotificationRule extends Rule {

  public final String email;

  public NotificationRule() {
    super();
    this.email = null;
  }

  public NotificationRule(
      RuleAttribute attribute,
      RuleSubject subject,
      List<Number> value,
      UnitType unit,
      ActionType action,
      String email) {
    super(attribute, subject, value, unit, action);
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    NotificationRule that = (NotificationRule) o;
    return Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), email);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "{ " + id + ", " + email + ", " + attribute + ", " + subject + ", " + value + ", " + unit
        + ", " + action + " }";
  }
}
