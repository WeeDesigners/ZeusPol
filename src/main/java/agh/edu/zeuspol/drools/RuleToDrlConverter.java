package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;

import java.util.List;

public class RuleToDrlConverter {

    private double EPSILON = 0.00000000001;

    private String packageName = "package drools;";
    private String metricImport = "import io.github.hephaestusmetrics.model.metrics.Metric;";
    private String actionServiceImport = "";
    private String metricClass = "Metric";
    private String dialectName = ""; //"dialect  \"mvel\"";
    private String ruleBegin = "rule";
    private String ruleEnd = "end";
    private String ruleWhen = "when";
    private String ruleThen = "then";


    public String convert(PolicyRule rule) {
        StringBuilder drlStringBuilder = new StringBuilder();
        drlStringBuilder.append(this.packageName).append("\n");
        drlStringBuilder.append(this.metricImport).append("\n");
        drlStringBuilder.append(this.actionServiceImport).append("\n");
        drlStringBuilder.append(this.dialectName).append("\n");
        drlStringBuilder
                .append(this.ruleBegin)
                .append(" \"")
                .append(this.ruleNameString(rule))
                .append("\"")
                .append("\n");
        drlStringBuilder.append(this.ruleWhen).append("\n");
        drlStringBuilder.append(this.metricClass)
                .append("(queryTag == ")
                .append(this.metricName(rule))
                .append(", ")
                .append(this.valueComparisonString(rule.relation, rule.value))
                .append(")")
                .append("\n");

        drlStringBuilder.append(this.ruleThen).append("\n");

        drlStringBuilder.append(this.actionString(rule));

        drlStringBuilder.append(this.ruleEnd).append("\n");
        return drlStringBuilder.toString();
    }

    private String actionString(PolicyRule rule) {
        ThemisActionBuilder themisActionBuilder = new ThemisActionBuilder(rule.action);

        themisActionBuilder.addParams(rule.params);
        themisActionBuilder.setActionName(rule.params.get("actionName"));
        themisActionBuilder.setCollectionName(rule.params.get("collectionName"));

        return themisActionBuilder.buildThemisAction();
    }

    private String metricName(PolicyRule rule) {
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

    private String ruleNameString(PolicyRule rule) {
        return (rule.id + "_" + rule.attribute + "_" + rule.subject + "_" + rule.unit + "_" + rule.value + "_" + rule.action).replaceAll("\\s+","");
    }
}
