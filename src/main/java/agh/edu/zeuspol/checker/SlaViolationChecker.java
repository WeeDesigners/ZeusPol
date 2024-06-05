package agh.edu.zeuspol.checker;

import agh.edu.zeuspol.datastructures.ActionType;
import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.Sla;

import java.util.List;

public class SlaViolationChecker {

    public boolean check(Sla sla, Rule rule){
        for (Rule slaRule: sla.getRules()){
            if (slaRule.subject == rule.subject && slaRule.attribute == rule.attribute){
                if (slaRule.unit != rule.unit){
                    throw new IllegalArgumentException();
                }
                if (slaRule.action == ActionType.BT && rule.action == ActionType.BT){
                    if (this.isBetween(slaRule.value, rule.value)){
                        return false;
                    }
                }
                else if (slaRule.action == ActionType.BT){
                    if (!(rule.action.apply(rule.value, slaRule.value.get(0)) && rule.action.apply(rule.value, slaRule.value.get(1)))){
                        return false;
                    }
                }
                else if (rule.action == ActionType.BT){

                }
                else if (slaRule.action != rule.action){
                    return false;
                }
                else {
                    if (handleSameAction(slaRule, rule)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isBetween(List<Number> span1, List<Number> span2){
        double n1 = span1.get(0).doubleValue();
        double n2 = span1.get(1).doubleValue();

        double m1 = span2.get(0).doubleValue();
        double m2 = span2.get(1).doubleValue();
        return n1 <= m1 && n1 <= m2 && n2 >= m1 && n2 >= m2;
    }

    private boolean handleSameAction(Rule slaRule, Rule rule) {
        ActionType action = slaRule.action;
        return action.apply(slaRule.value, rule.value.get(0));
    }

}
