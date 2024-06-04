package agh.edu.zeuspol.data_structures;

import java.util.List;

public class Rule {
    public final RuleAttribute attribute;
    public final RuleSubject subject;
    public final List<Number> value;
    public final UnitType unit;
    public final ActionType action;

    public Rule(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, ActionType action) {
        this.attribute = attribute;
        this.subject = subject;
        this.value = value;
        this.unit = unit;
        this.action = action;
    }

    @Override
    public String toString() {
        //TODO -> better concat
        return "{ " + attribute + ", " + subject + ", " + value + ", " + unit + ", " + action + " }";
    }
}
