package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.checker.SlaViolationChecker;
import agh.edu.zeuspol.datastructures.Rule;
import java.util.ArrayList;
import java.util.List;

public class Policies {

  private static Policies instance = new Policies();

  private final List<Rule> rules;

  private Policies() {
    this.rules = new ArrayList<>();
  }

  public static void setInstance(Policies policies) {
    instance = policies;
  }

  public static Policies getInstance() {
    return instance;
  }

  public void addRules(List<Rule> rules) {
    this.rules.addAll(rules);
  }

  public boolean addRulesSecure(List<Rule> rules) {
    if (!SlaViolationChecker.checkRules(rules)) {
      this.addRules(rules);
      return true;
    }
    return false;
  }

  public void addRule(Rule rule) {
    this.rules.add(rule);
  }

  public Rule removeRule(long id) {
    for (Rule rule : rules) {
      if (rule.id == id) {
        rules.remove(rule);
        return rule;
      }
    }
    return null;
  }

  public List<Rule> getRules() {
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
