package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.checker.SlaViolationChecker;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.base.Rule;
import java.util.ArrayList;
import java.util.List;

public class Policies {

  private static Policies instance = new Policies();

  private final List<PolicyRule> rules;

  private Policies() {
    this.rules = new ArrayList<>();
  }

  public static void setInstance(Policies policies) {
    instance = policies;
  }

  public static Policies getInstance() {
    return instance;
  }

  public void addRules(List<PolicyRule> rules) {
    this.rules.addAll(rules);
  }

  public boolean addRulesSecure(List<PolicyRule> rules) {
    //I have imagined how it would sound if person with "reranie" disorder tried to say "ruleRules" C:
    List<Rule> ruleRules = new ArrayList<>(rules);
    if (!SlaViolationChecker.checkRules(ruleRules)) {
      this.addRules(rules);
      return true;
    }
    return false;
  }

  public void addRule(PolicyRule rule) {
    this.rules.add(rule);
  }

  public PolicyRule removeRule(long id) {
    for (PolicyRule rule : rules) {
      if (rule.id == id) {
        rules.remove(rule);
        return rule;
      }
    }
    return null;
  }

  public List<PolicyRule> getRules() {
    return new ArrayList<>(rules);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "\n====================================\n "
        + "Policies:\n"
        + rules
        + "\n====================================\n";
  }
}
