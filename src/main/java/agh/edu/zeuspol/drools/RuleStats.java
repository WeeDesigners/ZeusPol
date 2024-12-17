package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RuleStats {
  public final long ruleId;
  int numFires = 0;
  LocalDateTime lastFired = null;

  public RuleStats(PolicyRule rule) {
    this.ruleId = rule.id;
  }

  public void fired() {
    this.numFires++;
    this.lastFired = LocalDateTime.now();
  }
}
