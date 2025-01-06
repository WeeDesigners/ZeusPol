package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import io.github.hephaestusmetrics.model.metrics.Metric;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RuleStats {
  public final long ruleId;
  public int fireCount = 0;
  public int maxRetry = 4;
  public LocalDateTime lastFired = LocalDateTime.MIN;
  public List<Metric> prevMetrics = null;

  public RuleStats(PolicyRule rule) {
    this.ruleId = rule.id;
    this.maxRetry = rule.maxRetry;
  }

  public RuleStats(SlaRule rule) {
    this.ruleId = rule.id;
  }

  public void fired() {
    this.fireCount++;
    this.lastFired = LocalDateTime.now();
  }

  public void fired(List<Metric> prevMetrics) {
    this.prevMetrics = new ArrayList<>(prevMetrics);
    this.fired();
  }
}
