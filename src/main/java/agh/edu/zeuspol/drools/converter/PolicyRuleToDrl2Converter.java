package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.impl.*;

import java.util.List;

public class PolicyRuleToDrl2Converter {

    public DrlStringFile convert(PolicyRule policyRule) {
        PolicyRuleDrlConditionBuilder conditionBuilder = new PolicyRuleDrlConditionBuilder(policyRule);
        PolicyRuleCountActionBuilder actionBuilder = new PolicyRuleCountActionBuilder(policyRule);
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
                        List.of(actionBuilder));

        return toDrlConverter.convert();
    }
}
