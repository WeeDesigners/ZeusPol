package agh.edu.zeuspol.datastructures.types;

import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.datastructures.types.base.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlaRule extends Rule {

    public SlaRule(RuleAttribute attribute, RuleSubject subject, List<Number> value, UnitType unit, RelationType action) {
        super(attribute, subject, value, unit, action);
    }

    public List<PolicyRule> toPolicyRules(){
        List<PolicyRule> rules = new ArrayList<>();
        switch (this.relation){
            case EQ -> {
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.LT, Action.DEFAULT, new Params()));
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.GT, Action.DEFAULT, new Params()));
            }
            case GT -> {
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.LT, Action.DEFAULT, new Params()));
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.EQ, Action.DEFAULT, new Params()));
            }
            case LT -> {
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.GT, Action.DEFAULT, new Params()));
                rules.add(new PolicyRule(attribute, subject, value, unit, RelationType.EQ, Action.DEFAULT, new Params()));
            }
            case BT -> {
                List<Number> n1 = Collections.singletonList(value.get(0));
                List<Number> n2 = Collections.singletonList(value.get(1));
                rules.add(new PolicyRule(attribute, subject, n2, unit, RelationType.GT, Action.DEFAULT, new Params()));
                rules.add(new PolicyRule(attribute, subject, n1, unit, RelationType.LT, Action.DEFAULT, new Params()));
            }
        }
        return rules;
    }
}
