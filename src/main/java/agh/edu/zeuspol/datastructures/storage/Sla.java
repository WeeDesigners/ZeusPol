package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.SlaType;
import java.util.ArrayList;
import java.util.List;

public class Sla {
  public final long id;
  public final long clientId;
  public final long applicationId;
  public final SlaType slaType;
  private final List<SlaRule> slaRules;

  public Sla(long id, long clientId, long applicationId, SlaType slaType) {
    this.id = id;
    this.clientId = clientId;
    this.applicationId = applicationId;
    this.slaType = slaType;
    this.slaRules = new ArrayList<>();
  }
}
