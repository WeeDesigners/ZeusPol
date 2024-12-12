package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.drools.DrlStringFile;

/** To use converted drl you have to add */
public class RuleToDrlWithStatsConverter extends RuleToDrlConverter {

  public RuleToDrlWithStatsConverter() {
    super();
    this.addImport("import agh.edu.zeuspol.drools.RuleStats;");
  }

  public RuleToDrlWithStatsConverter(ThemisActionBuilder themisActionBuilder) {
    super(themisActionBuilder);
    this.addImport("import agh.edu.zeuspol.drools.RuleStats;");
  }

  @Override
  public DrlStringFile convert(PolicyRule rule) {
    StringBuilder drlStringBuilder = new StringBuilder();

    this.appendImports(drlStringBuilder);
    this.appendRuleBegin(drlStringBuilder, rule);
    this.appendRuleCondition(drlStringBuilder, rule);
    this.appendRuleStatsCondition(drlStringBuilder, rule);
    this.appendThemisAction(drlStringBuilder, rule);
    this.appendRuleStatsUpdate(drlStringBuilder);
    this.appendEnd(drlStringBuilder);

    return new DrlStringFile(String.valueOf(rule.id), drlStringBuilder.toString());
  }

  protected void appendRuleStatsCondition(StringBuilder drlStringBuilder, PolicyRule rule) {
    drlStringBuilder
        .append("$stats: RuleStats(id == ")
        .append(rule.id)
        .append(",lastFired != null)")
        .append("\n");
  }

  protected void appendRuleStatsUpdate(StringBuilder drlStringBuilder) {
    drlStringBuilder.append("stats.fired()").append("\n");
  }
}
