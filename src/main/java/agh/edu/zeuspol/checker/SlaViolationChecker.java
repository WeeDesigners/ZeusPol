package agh.edu.zeuspol.checker;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.Sla;

import java.util.List;

public class SlaViolationChecker {

    public boolean check(Sla sla, Rule rule){
        for (Rule slaRule: sla.getRules()){
            if (this.rulesSameSubject(slaRule, rule)){

            }
        }
        return false;
    }

    private boolean rulesSameSubject(Rule rule1, Rule rule2){
        return rule1.subject == rule2.subject && rule1.attribute == rule2.attribute;
    }

}
