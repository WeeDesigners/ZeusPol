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
  private final List<SlaRule> slaRules;

  public Sla(long id, String clientId, String applicationId, SlaType type, List<SlaRule> slaRules) {
    this.id = id;
    this.clientId = clientId;
    this.applicationId = applicationId;
    this.slaRules = new ArrayList<>();
  }

  public boolean addRules(List<SlaRule> rules){
    for(SlaRule rule : rules){
      if(this.addRule(rule)){
        return false;
      }
    }
    return true;
  }

  public boolean addRule(SlaRule rule){
    if(!checkUniqueId(rule.id)){
      return false;
    }
    else {
      this.slaRules.add(rule);
    }
    return true;
  }

  public SlaRule removeRule(long id){
    for(SlaRule rule : slaRules){
      if(rule.id == id){
        slaRules.remove(rule);
        return rule;
      }
    }
    return null;
  }

  public void removeRules(){
    this.slaRules.clear();
  }

  public List<SlaRule> getRules() {
    return new ArrayList<>(slaRules);
  }


  @Override
  public String toString() {
    //TODO -> better concat
    return "\n====================================\n Rules:\n"
            + slaRules
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
