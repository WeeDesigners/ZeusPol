package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.storage.Policies;
import java.time.Duration;
import java.time.LocalDateTime;

public class PolicySlaViolationCheck {

  public static void policyViolationCheck() {
    Policies policies = Policies.getInstance();
    for (RuleStats stats : policies.getRulesStatsList()) {
      LocalDateTime now = LocalDateTime.now();
      if (stats.lastFired == null) {
        continue;
      }
      Duration duration = Duration.between(stats.lastFired, now);
      if (duration.toMinutes() < 5 && duration.toMinutes() >= 0) {
        System.out.println("Id of policy that potentially violated the Sla: " + stats.ruleId);
      }
    }
  }
}
