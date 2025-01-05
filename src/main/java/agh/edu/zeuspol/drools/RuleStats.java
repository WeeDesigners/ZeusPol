package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import java.time.LocalDateTime;

public class RuleStats {
  public final long ruleId;
  int numFires = 0;
  public LocalDateTime lastFired = LocalDateTime.MIN;

  public RuleStats(PolicyRule rule) {
    this.ruleId = rule.id;
  }

  public RuleStats(SlaRule rule) {
    this.ruleId = rule.id;
  }

  public void fired() {
    this.numFires++;
    this.lastFired = LocalDateTime.now();
  }
}
