package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;

import java.util.List;

public class StatsDrlActionBuilder extends DrlActionBuilder {

    private PolicyRule policyRule;

    public StatsDrlActionBuilder(PolicyRule policyRule) {
        this.policyRule = policyRule;
    }

    @Override
    public String build() {
        return "$stats.fired();";
    }

    @Override
    public List<String> imports() {
        return List.of(
                "import agh.edu.zeuspol.drools.RuleStats;"
        );
    }
}
