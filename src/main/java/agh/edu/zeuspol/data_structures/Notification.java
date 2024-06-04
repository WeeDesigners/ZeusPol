package agh.edu.zeuspol.data_structures;

import javax.xml.crypto.Data;
import java.util.List;

public class Notification extends Rule {

    public final String email;

    public Notification(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, ActionType action, String email) {
        super(attribute, subject, value, unit, action);
        this.email = email;
    }

}
