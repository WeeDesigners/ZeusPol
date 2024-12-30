package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import agh.edu.zeuspol.drools.builder.base.DrlConditionBuilder;
import agh.edu.zeuspol.drools.builder.base.DrlImportsBuilder;
import agh.edu.zeuspol.drools.builder.base.DrlNameBuilder;
import java.util.List;

public class ToDrlConverter {

  private String packageName = "package drools;";
  private String ruleBegin = "rule";
  private String ruleEnd = "end";
  private String ruleWhen = "when";
  private String ruleThen = "then";

  private DrlConditionBuilder drlConditionBuilder;
  private DrlNameBuilder drlRuleNameBuilder;
  private DrlImportsBuilder importsBuilder;
  private List<DrlActionBuilder> drlActionBuilders;

  public ToDrlConverter(
      DrlImportsBuilder importsBuilder,
      DrlNameBuilder drlRuleNameBuilder,
      DrlConditionBuilder drlConditionBuilder,
      List<DrlActionBuilder> drlActionBuilders) {
    this.drlConditionBuilder = drlConditionBuilder;
    this.drlRuleNameBuilder = drlRuleNameBuilder;
    this.importsBuilder = importsBuilder;
    this.drlActionBuilders = drlActionBuilders;
  }

  public DrlStringFile convert() {

    String drlStringBuilder =
        this.packageName()
            + this.imports()
            + this.ruleBegin()
            + this.when()
            + this.conditions()
            + this.then()
            + this.actions()
            + this.end();

    return new DrlStringFile(this.drlRuleNameBuilder.build(), drlStringBuilder);
  }

  protected String packageName() {
    return this.packageName + "\n";
  }

  protected String imports() {

    return this.importsBuilder.build();
  }

  protected String ruleBegin() {
    return this.ruleBegin + " \"" + this.drlRuleNameBuilder.build() + "\"" + "\n";
  }

  protected String when() {
    return this.ruleWhen + "\n";
  }

  protected String conditions() {
    return drlConditionBuilder.build();
  }

  protected String then() {
    return this.ruleThen + "\n";
  }

  protected String actions() {
    StringBuilder actions = new StringBuilder();

    for (DrlActionBuilder actionBuilder : this.drlActionBuilders) {
      actions.append(actionBuilder.build());
    }

    return actions.toString() + "\n";
  }

  protected String end() {
    return this.ruleEnd;
  }
}
