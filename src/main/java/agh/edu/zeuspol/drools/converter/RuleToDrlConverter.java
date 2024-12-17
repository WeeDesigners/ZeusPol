package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.Condition;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.DrlStringFile;
import java.util.ArrayList;
import java.util.List;

public class RuleToDrlConverter {

  private double EPSILON = 0.00000000001;

  private ThemisActionBuilder themisActionBuilder;

  private String packageName = "package drools;";
  private String metricImport = "import io.github.hephaestusmetrics.model.metrics.Metric;";
  private List<String> otherImports = new ArrayList<>();
  private String actionServiceImport = "";
  private String metricClass = "Metric";
  private String dialectName = "";
  private String ruleBegin = "rule";
  private String ruleEnd = "end";
  private String ruleWhen = "when";
  private String ruleThen = "then";

  //    TODO - Consider deleting default ThemisActionBuilder
  public RuleToDrlConverter() {
    this.themisActionBuilder = new CurlThemisActionBuilder();
  }

  public RuleToDrlConverter(ThemisActionBuilder themisActionBuilder) {
    this.themisActionBuilder = themisActionBuilder;
  }

  public DrlStringFile convert(PolicyRule rule) {
    StringBuilder drlStringBuilder = new StringBuilder();

    this.appendImports(drlStringBuilder);
    this.appendRuleBegin(drlStringBuilder, rule);
    this.appendRuleConditions(drlStringBuilder, rule);
    this.appendThemisAction(drlStringBuilder, rule);
    this.appendEnd(drlStringBuilder);

    return new DrlStringFile(String.valueOf(rule.id), drlStringBuilder.toString());
  }

  public void addImport(String stringImport) {
    this.otherImports.add(stringImport);
  }

  //    Methods that builds rule

  protected void appendImports(StringBuilder drlStringBuilder) {
    drlStringBuilder.append(this.packageName).append("\n");
    drlStringBuilder.append(this.metricImport).append("\n");
    for (String imp : this.themisActionBuilder.importsNeeded()) {
      drlStringBuilder.append(imp).append("\n");
    }
    for (String imp : this.otherImports) {
      drlStringBuilder.append(imp).append("\n");
    }
    drlStringBuilder.append(this.actionServiceImport).append("\n");
  }

  protected void appendRuleBegin(StringBuilder drlStringBuilder, PolicyRule rule) {
    drlStringBuilder
        .append(this.ruleBegin)
        .append(" \"")
        .append(rule.name)
        .append("\"")
        .append("\n");
    drlStringBuilder.append(this.ruleWhen).append("\n");
  }

  protected void appendRuleConditions(StringBuilder drlStringBuilder, PolicyRule rule) {
    for(Condition cond: rule.getConditions()){
      drlStringBuilder
              .append(this.metricClass)
              .append("(queryTag == \"")
              .append(cond.metric)
              .append("\", ")
              .append(this.valueComparisonString(cond.relation, cond.value))
              .append(")")
              .append("\n");
    }
  }

  protected void appendThemisAction(StringBuilder drlStringBuilder, PolicyRule rule) {
    drlStringBuilder.append(this.ruleThen).append("\n");
    drlStringBuilder.append(this.actionString(rule));
  }

  protected void appendEnd(StringBuilder drlStringBuilder) {
    drlStringBuilder.append(this.ruleEnd).append("\n");
  }

  private String actionString(PolicyRule rule) {
    return themisActionBuilder.buildThemisAction(rule.action);
  }

  private String valueComparisonString(RelationType actionType, double value) {
    return switch (actionType) {
      case GT -> "value >= %s".formatted(value);
      case LT -> "value <= %s".formatted(value);
      case EQ -> "value == %s".formatted(value);
      case BT -> throw new IllegalArgumentException("Between relation not supported");
    };
  }
}
