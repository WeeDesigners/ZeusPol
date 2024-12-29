package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.impl.HttpClientThemisActionBuilder;
import agh.edu.zeuspol.drools.builder.impl.PolicyRuleDrlConditionBuilder;
import agh.edu.zeuspol.drools.builder.impl.SimpleDrlNameBuilder;
import agh.edu.zeuspol.drools.builder.impl.StatsDrlActionBuilder;
import java.util.List;

public class PolicyRuleToDrlConverter {

  public DrlStringFile convert(PolicyRule policyRule) {
    PolicyRuleDrlConditionBuilder conditionBuilder = new PolicyRuleDrlConditionBuilder(policyRule);
    HttpClientThemisActionBuilder actionBuilder =
        new HttpClientThemisActionBuilder(policyRule.action);
    StatsDrlActionBuilder statsDrlActionBuilder = new StatsDrlActionBuilder(policyRule);
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
