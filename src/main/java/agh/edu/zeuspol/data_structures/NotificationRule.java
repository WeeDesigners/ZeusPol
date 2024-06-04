package agh.edu.zeuspol.data_structures;

import java.util.List;

public class NotificationRule extends Rule {

    public final String email;

    public NotificationRule(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, ActionType action, String email) {
        super(attribute, subject, value, unit, action);
        this.email = email;
    }

}
