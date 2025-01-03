package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.RuleStats;
import java.util.ArrayList;
import java.util.List;

public class Policies {

  private static Policies instance = new Policies();

  private final List<PolicyRule> rules = new ArrayList<>();
  ;
  private List<RuleStats> rulesStatsList = new ArrayList<>();

  private Policies() {}

  public static Policies newInstance() {
    instance = new Policies();
    return instance;
  }

  public static Policies getInstance() {
    return instance;
  }

  public void addRules(List<PolicyRule> rules) {
    for (PolicyRule rule : rules) {
      this.addRule(rule);
    }
  }

  public void addRule(PolicyRule rule) {
    this.rules.add(rule);
    this.rulesStatsList.add(new RuleStats(rule));
  }

  public List<PolicyRule> getRules() {
    return new ArrayList<>(rules);
  }

  public List<RuleStats> getRulesStatsList() {
    return rulesStatsList;
  }

  @Override
  public String toString() {
    return "\n====================================\n "
        + "Policies:\n"
        + rules
        + "\n====================================\n";
  }
}
