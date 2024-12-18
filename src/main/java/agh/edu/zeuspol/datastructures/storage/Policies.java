package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.RuleStats;
import java.util.ArrayList;
import java.util.List;

public class Policies {

  private static Policies instance = new Policies();

  private final List<PolicyRule> rules;

  private final List<RuleStats> rulesStatsList;

  private Policies() {
    this.rules = new ArrayList<>();
    this.rulesStatsList = new ArrayList<>();
  }

  public static void setInstance(Policies policies) {
    instance = policies;
  }

  public static Policies getInstance() {
    return instance;
  }

  public void addRules(List<PolicyRule> rules) {
    for (PolicyRule r: rules){
      this.addRule(r);
    }
  }

  //  public boolean addRulesSecure(List<PolicyRule> rules) {
  //    // I have imagined how it would sound if person with "reranie" disorder tried to say
  // "ruleRules"
  //    // C:
  //    List<Rule> ruleRules = new ArrayList<>(rules);
  //    if (!SlaViolationChecker.checkRules(ruleRules)) {
  //      this.addRules(rules);
  //      return true;
  //    }
  //    return false;
  //  }

  public void addRule(PolicyRule rule) {
    this.rules.add(rule);
    this.rulesStatsList.add(new RuleStats(rule));
  }

  public PolicyRule removeRule(long id) {
    for (PolicyRule rule : rules) {
      if (rule.id == id) {
        rules.remove(rule);
        for (RuleStats stats : this.rulesStatsList) {
          if (stats.ruleId == id) {
            this.rulesStatsList.remove(stats);
            break;
          }
        }
        return rule;
      }
    }
    return null;
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
