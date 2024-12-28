package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;

import java.util.List;

public class PolicySlaViolationDrlActionBuilder extends DrlActionBuilder {

    @Override
    public String build() {
        return "PolicySlaViolationCheck.policyViolationCheck();\n";
    }

    @Override
    public List<String> imports() {
        return List.of(
                "import agh.edu.zeuspol.drools.PolicySlaViolationCheck;"
        );
    }
}
