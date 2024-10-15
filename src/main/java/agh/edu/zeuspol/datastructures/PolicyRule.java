package agh.edu.zeuspol.datastructures;

import java.util.List;
import java.util.Objects;

public class PolicyRule extends Rule{

    public final Action action;
    public Params params;

    public PolicyRule(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, RelationType relation, Action action, Params params) {
        super(attribute, subject, value, unit, relation);
        this.action = action;
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        PolicyRule rule = (PolicyRule) o;
        return super.equals(o)
                && this.action == rule.action
                && this.params.equals(rule.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, subject, value, unit, relation, action, params);
    }

    @Override
    public String toString() {
        // TODO -> better concat
        return "{ " + id + ", " + attribute + ", " + subject + ", " + value + ", " + unit + ", "
                + relation + ", " + action + ", " + params + " }";
    }
}
