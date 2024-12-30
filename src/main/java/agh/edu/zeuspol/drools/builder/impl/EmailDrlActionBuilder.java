package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import java.util.List;

public class EmailDrlActionBuilder extends DrlActionBuilder {

  private Sla sla;
  private SlaRule slaRule;

  public EmailDrlActionBuilder(Sla sla, SlaRule slaRule) {
    this.sla = sla;
    this.slaRule = slaRule;
  }

  @Override
  public String build() {
    return "SlaViolationEmailNotification.sendNotification("
        + this.sla.id
        + ","
        + "\""
        + this.sla.getClientId()
        + "\""
        + ","
        + "\""
        + this.sla.getApplicationId()
        + "\""
        + ","
        + "\""
        + this.slaRule.toString()
        + "\""
        + ");\n";
  }

  @Override
  public List<String> imports() {
    return List.of("import agh.edu.zeuspol.services.SlaViolationEmailNotification;");
  }
}
