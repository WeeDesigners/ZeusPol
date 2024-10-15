package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.datastructures.SlaRule;

import java.util.ArrayList;
import java.util.List;

public class Sla {

  private static Sla instance = new Sla();

  private final List<SlaRule> rules;

  private Sla() {
    this.rules = new ArrayList<>();
  }

  public static void setInstance(Sla sla) {
    instance = sla;
  }

  public static Sla getInstance() {
    return instance;
  }

  public void addRules(List<SlaRule> rules) {
    this.rules.addAll(rules);
  }

  public void addRule(SlaRule rule) {
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

  public void removeRules() {
    this.rules.clear();
  }


  public void clearSla() {
    removeRules();
  }

  public List<Rule> getRules() {
    return new ArrayList<>(rules);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "\n====================================\n"
        + "SLA:\n"
        + "------------------------------------\n"
        + "Rules:\n"
        + rules
        + "\n====================================\n";
  }
}
