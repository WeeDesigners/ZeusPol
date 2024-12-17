package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.SlaType;
import java.util.ArrayList;
import java.util.List;

public class Sla {
  public final long id;
  private SlaType type;
  private String clientId;
  private String applicationId;
  private final List<SlaRule> rules;

  public Sla(long id, String clientId, String applicationId, SlaType type, List<SlaRule> slaRules) {
    this.id = id;
    this.clientId = clientId;
    this.applicationId = applicationId;
    this.rules = new ArrayList<>();
  }

  public boolean addRules(List<SlaRule> rules) {
    for (SlaRule rule : rules) {
      if (this.addRule(rule)) {
        return false;
      }
    }
    return true;
  }

  public boolean addRule(SlaRule rule) {
    if (!checkUniqueId(rule.id)) {
      return false;
    } else {
      this.rules.add(rule);
    }
    return true;
  }

  private boolean checkUniqueId(long id) {
    for (SlaRule rule : rules) {
      if (rule.id == id) {
        return false;
      }
    }
    return true;
  }

  public SlaRule removeRule(long id) {
    for (SlaRule rule : rules) {
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

  public List<SlaRule> getRules() {
    return new ArrayList<>(rules);
  }

  @Override
  public String toString() {
    // TODO -> better concat
    return "\n====================================\n"
        + "id: "
        + id
        + "\n"
        + "type: "
        + type
        + "\n"
        + "clientId: "
        + clientId
        + "\n"
        + "applicationId: "
        + applicationId
        + "\n"
        + "rules:\n"
        + rules
        + "\n====================================\n";
  }

  public SlaType getType() {
    return type;
  }

  public void setType(SlaType type) {
    this.type = type;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }
}
