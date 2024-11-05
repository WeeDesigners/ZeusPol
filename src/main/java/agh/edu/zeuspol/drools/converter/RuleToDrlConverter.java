package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.RuleStats;

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
    this.appendRuleCondition(drlStringBuilder, rule);
    this.appendThemisAction(drlStringBuilder, rule);
    this.appendEnd(drlStringBuilder);

    return new DrlStringFile(this.ruleNameString(rule), drlStringBuilder.toString());
  }

  public void addImport(String stringImport) {
    this.otherImports.add(stringImport);
  }

  //    Methods that builds rule

  protected void appendImports(StringBuilder drlStringBuilder) {
    drlStringBuilder.append(this.packageName).append("\n");
    drlStringBuilder.append(this.metricImport).append("\n");
    for (String imp: this.themisActionBuilder.importsNeeded()){
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
            .append(this.ruleNameString(rule))
            .append("\"")
            .append("\n");
    drlStringBuilder.append(this.ruleWhen).append("\n");
  }

  protected void appendRuleCondition(StringBuilder drlStringBuilder, PolicyRule rule) {
    drlStringBuilder
            .append(this.metricClass)
            .append("(queryTag == ")
            .append(this.metricNameString(rule))
            .append(", ")
            .append(this.valueComparisonString(rule.relation, rule.value))
            .append(")")
            .append("\n");
  }

  protected void appendThemisAction(StringBuilder drlStringBuilder, PolicyRule rule) {
    drlStringBuilder.append(this.ruleThen).append("\n");
    drlStringBuilder.append(this.actionString(rule));
  }

  protected void appendEnd(StringBuilder drlStringBuilder) {
    drlStringBuilder.append(this.ruleEnd).append("\n");
  }

  private String actionString(PolicyRule rule) {
    this.themisActionBuilder.setCollectionName(rule.params.get("collectionName"));
    this.themisActionBuilder.setActionName(rule.params.get("actionName"));
    this.themisActionBuilder.setAction(rule.action);
    themisActionBuilder.addParams(rule.params);
    String themisActionString = themisActionBuilder.buildThemisAction();

    themisActionBuilder.reset();

    return themisActionString;
  }

  private String metricNameString(PolicyRule rule) {
    return "\"" + rule.subject.toString() + "_" + rule.unit.toString() + "\"";
  }

  private String valueComparisonString(RelationType actionType, List<Number> numbers) {
    return switch (actionType) {
      case GT -> "value > %s".formatted(numbers.get(0));
      case LT -> "value < %s".formatted(numbers.get(0));
      case EQ -> "value - %s < %s".formatted(numbers.get(0), this.EPSILON);
      case BT -> "value >= %s && value <= %s".formatted(numbers.get(0), numbers.get(1));
    };
  }

  protected String ruleNameString(PolicyRule rule) {
    return (rule.id
            + "_"
            + rule.attribute
            + "_"
            + rule.subject
            + "_"
            + rule.unit
            + "_"
            + rule.value
            + "_"
            + rule.action)
        .replaceAll("\\s+", "");
  }
}
