package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.impl.EmailDrlActionBuilder;
import agh.edu.zeuspol.drools.builder.impl.PolicySlaViolationDrlActionBuilder;
import agh.edu.zeuspol.drools.builder.impl.SimpleDrlNameBuilder;
import agh.edu.zeuspol.drools.builder.impl.SlaRuleDrlConditionBuilder;
import java.util.List;

public class SlaRuleToDrlConverter {

  public DrlStringFile convert(Sla sla, SlaRule slaRule) {
    SlaRuleDrlConditionBuilder conditionBuilder = new SlaRuleDrlConditionBuilder(slaRule);
    PolicySlaViolationDrlActionBuilder policySlaViolationDrlActionBuilder = new PolicySlaViolationDrlActionBuilder();
    EmailDrlActionBuilder actionBuilder = new EmailDrlActionBuilder(sla, slaRule);
    SimpleDrlNameBuilder nameBuilder = new SimpleDrlNameBuilder("SLA_" + slaRule.id);
    DrlImportsBuilder importsBuilder = new DrlImportsBuilder();

    importsBuilder.addImports(actionBuilder.imports());
    importsBuilder.addImports(conditionBuilder.imports());
    importsBuilder.addImports(policySlaViolationDrlActionBuilder.imports());

    ToDrlConverter toDrlConverter = new ToDrlConverter(importsBuilder, nameBuilder, conditionBuilder, List.of(actionBuilder, policySlaViolationDrlActionBuilder));

    return toDrlConverter.convert();
  }
}
