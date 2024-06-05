package agh.edu.zeuspol.datastructures;

import java.util.List;

public class NotificationRule extends Rule {

    public final String email;

    public NotificationRule(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, ActionType action, String email) {
        super(attribute, subject, value, unit, action);
        this.email = email;
    }

    @Override
    public String toString() {
        //TODO -> better concat
        return "{ " + id + ", " + email + ", " + attribute + ", " + subject + ", " + value + ", " + unit + ", " + action + " }";
    }

}
