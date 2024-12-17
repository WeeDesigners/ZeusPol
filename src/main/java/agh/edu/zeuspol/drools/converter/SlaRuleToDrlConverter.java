package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.Condition;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.PolicySlaViolationCheck;

import java.util.ArrayList;
import java.util.List;

public class SlaRuleToDrlConverter extends RuleToDrlConverter {

    private EmailActionBuilder emailActionBuilder = new EmailActionBuilder();
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

    public DrlStringFile convert(Sla sla, SlaRule rule) {
        StringBuilder drlStringBuilder = new StringBuilder();

        this.appendImports(drlStringBuilder);
        this.appendRuleBegin(drlStringBuilder, rule);
        this.appendRuleConditions(drlStringBuilder, rule);
        this.appendThemisAction(drlStringBuilder, sla, rule);
        drlStringBuilder.append("PolicySlaViolationCheck.policyViolationCheck();\n");
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
        drlStringBuilder.append("import agh.edu.zeuspol.drools.PolicySlaViolationCheck;\n");
        for (String imp : this.emailActionBuilder.importsNeeded()) {
            drlStringBuilder.append(imp).append("\n");
        }
        for (String imp : this.otherImports) {
            drlStringBuilder.append(imp).append("\n");
        }
        drlStringBuilder.append(this.actionServiceImport).append("\n");
    }

    protected void appendRuleBegin(StringBuilder drlStringBuilder, SlaRule rule) {
        drlStringBuilder
                .append(this.ruleBegin)
                .append(" \"")
                .append(rule.id)
                .append("\"")
                .append("\n");
        drlStringBuilder.append(this.ruleWhen).append("\n");
    }

    protected void appendRuleConditions(StringBuilder drlStringBuilder, SlaRule rule) {
        for (Condition cond : rule.getConditions()) {
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

    protected void appendThemisAction(StringBuilder drlStringBuilder,Sla sla, SlaRule rule) {
        drlStringBuilder.append(this.ruleThen).append("\n");
        drlStringBuilder.append(this.actionString(sla, rule)).append("\n");
    }

    protected void appendEnd(StringBuilder drlStringBuilder) {
        drlStringBuilder.append(this.ruleEnd).append("\n");
    }

    private String actionString(Sla sla, SlaRule rule) {
        return this.emailActionBuilder.buildEmailAction(sla, rule);
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
