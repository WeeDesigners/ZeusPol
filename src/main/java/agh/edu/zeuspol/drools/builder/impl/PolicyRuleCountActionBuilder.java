package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import java.util.ArrayList;
import java.util.List;

public class PolicyRuleCountActionBuilder extends DrlActionBuilder {

  private PolicyRule policyRule;
  private ThemisServiceActionBuilder themisServiceActionBuilder;

  public PolicyRuleCountActionBuilder(PolicyRule policyRule) {
    this.policyRule = policyRule;
    this.themisServiceActionBuilder = new ThemisServiceActionBuilder(policyRule.action);
  }

  @Override
  public String build() {
    StringBuilder drlStringBuilder = new StringBuilder();

    drlStringBuilder.append("if (\n").append("$stats.prevMetrics != null &&\n");

    for (int i = 0; i < 1; i++) {
      drlStringBuilder
          .append(valueComparisonString(i, this.policyRule.getConditions().get(i).relation))
          .append(" &&\n");
    }
    drlStringBuilder.delete(drlStringBuilder.length() - 4, drlStringBuilder.length());
    drlStringBuilder.append(") {\n");

    drlStringBuilder.append("$stats.fireCount = 0;\n");

    drlStringBuilder.append("}\n");

    drlStringBuilder.append(
        "if ($stats.fireCount >= $stats.maxRetry && $stats.maxRetry != -1) {\n");

    drlStringBuilder.append(
        """
        StalePolicyRuleNotification.sendNotification(%s, "%s", "%s", "%s");

        """
            .formatted(
                policyRule.id,
                policyRule.name,
                policyRule.getConditions().toString(),
                policyRule.action.toString()));

    drlStringBuilder.append("}\n");

    drlStringBuilder.append("else {\n");

    drlStringBuilder.append(themisServiceActionBuilder.build());

    drlStringBuilder.append("}\n");

    drlStringBuilder.append(
        "$stats.fired(%s);\n"
            .formatted(this.listOfMetricsString(this.policyRule.getConditions().size())));

    return drlStringBuilder.toString();
  }

  @Override
  public List<String> imports() {
    List<String> importsNeeded = new ArrayList<>(this.themisServiceActionBuilder.imports());
    importsNeeded.add("import agh.edu.zeuspol.drools.builder.action.StalePolicyRuleNotification;");
    importsNeeded.add("import agh.edu.zeuspol.drools.RuleStats;");
    importsNeeded.add("import java.util.List;");
    return importsNeeded;
  }

  private String valueComparisonString(int metricIndex, RelationType actionType) {
    return switch (actionType) {
      case GT ->
          "$stats.prevMetrics.get(%s).value > $m%s.value".formatted(metricIndex, metricIndex);
      case LT ->
          "$stats.prevMetrics.get(%s).value < $m%s.value".formatted(metricIndex, metricIndex);
      case EQ -> throw new IllegalArgumentException("Between relation not supported");
      case BT -> throw new IllegalArgumentException("Between relation not supported");
    };
  }

  private String listOfMetricsString(int metricsNum) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("List.of(");
    for (int i = 0; i < metricsNum; i++) {
      stringBuilder.append("$m").append(i).append(", ");
    }
    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}
