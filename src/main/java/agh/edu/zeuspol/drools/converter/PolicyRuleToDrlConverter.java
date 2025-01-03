package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.impl.*;
import java.util.List;

public class PolicyRuleToDrlConverter {

  public DrlStringFile convert(PolicyRule policyRule) {
    PolicyRuleDrlConditionBuilder conditionBuilder = new PolicyRuleDrlConditionBuilder(policyRule);
    ThemisServiceActionBuilder actionBuilder = new ThemisServiceActionBuilder(policyRule.action);
    StatsDrlActionBuilder statsDrlActionBuilder = new StatsDrlActionBuilder();
    SimpleDrlNameBuilder nameBuilder =
        new SimpleDrlNameBuilder(policyRule.id + "_" + policyRule.name);
    DrlImportsBuilder importsBuilder = new DrlImportsBuilder();

    importsBuilder.addImports(actionBuilder.imports());
    importsBuilder.addImports(conditionBuilder.imports());

    ToDrlConverter toDrlConverter =
        new ToDrlConverter(
            importsBuilder,
            nameBuilder,
            conditionBuilder,
            List.of(actionBuilder, statsDrlActionBuilder));

    return toDrlConverter.convert();
  }
}
