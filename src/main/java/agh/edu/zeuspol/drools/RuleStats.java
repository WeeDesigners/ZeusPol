package agh.edu.zeuspol.drools;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import java.time.LocalDate;


public class RuleStats {
    long ruleId;
    int numFires = 0;
    LocalDate lastFired = null;

    public RuleStats(PolicyRule rule) {
        this.ruleId = rule.id;
    }

    public void fired() {
        this.numFires++;
        this.lastFired = LocalDate.now();
    }
}
