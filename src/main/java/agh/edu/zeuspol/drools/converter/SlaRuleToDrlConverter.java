package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.impl.*;

import java.util.List;

public class SlaRuleToDrlConverter {

  public DrlStringFile convert(Sla sla, SlaRule slaRule) {
    SlaRuleDrlConditionBuilder conditionBuilder = new SlaRuleDrlConditionBuilder(slaRule);
    PolicySlaViolationDrlActionBuilder policySlaViolationDrlActionBuilder =
        new PolicySlaViolationDrlActionBuilder();
    EmailDrlActionBuilder actionBuilder = new EmailDrlActionBuilder(sla, slaRule);
    StatsDrlActionBuilder statsDrlActionBuilder = new StatsDrlActionBuilder();
    SimpleDrlNameBuilder nameBuilder = new SimpleDrlNameBuilder("SLA_" + slaRule.id);
    DrlImportsBuilder importsBuilder = new DrlImportsBuilder();

    importsBuilder.addImports(actionBuilder.imports());
    importsBuilder.addImports(conditionBuilder.imports());
    importsBuilder.addImports(policySlaViolationDrlActionBuilder.imports());
    importsBuilder.addImports(statsDrlActionBuilder.imports());

    ToDrlConverter toDrlConverter =
        new ToDrlConverter(
            importsBuilder,
            nameBuilder,
            conditionBuilder,
            List.of(actionBuilder, policySlaViolationDrlActionBuilder, statsDrlActionBuilder));

    return toDrlConverter.convert();
  }
}
