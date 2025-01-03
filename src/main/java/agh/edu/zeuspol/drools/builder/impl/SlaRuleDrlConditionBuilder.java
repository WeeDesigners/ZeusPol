package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.Condition;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.builder.base.DrlConditionBuilder;
import java.util.List;

public class SlaRuleDrlConditionBuilder extends DrlConditionBuilder {

  private SlaRule slaRule;

  public SlaRuleDrlConditionBuilder(SlaRule slaRule) {
    this.slaRule = slaRule;
  }

  @Override
  public String build() {
    StringBuilder drlStringBuilder = new StringBuilder();
    for (Condition cond : slaRule.getConditions()) {
      drlStringBuilder
          .append("Metric")
          .append("(queryTag == \"")
          .append(cond.metric)
          .append("\", ")
          .append(this.valueComparisonString(cond.relation, cond.value))
          .append(")")
          .append("\n");
    }
    drlStringBuilder
            .append("$stats: RuleStats(ruleId == ")
            .append(this.slaRule.id)
            .append(")")
            .append("\n");
    return drlStringBuilder.toString();
  }

  @Override
  public List<String> imports() {
    return List.of("import io.github.hephaestusmetrics.model.metrics.Metric;");
  }

  private String valueComparisonString(RelationType actionType, double value) {
    return switch (actionType) {
      case GT -> "value <= %s".formatted(value);
      case LT -> "value >= %s".formatted(value);
      case EQ -> "value != %s".formatted(value);
      case BT -> throw new IllegalArgumentException("Between relation not supported");
    };
  }
}
