package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import java.util.List;

public class StatsDrlActionBuilder extends DrlActionBuilder {

  @Override
  public String build() {
    return "$stats.fired();";
  }

  @Override
  public List<String> imports() {
    return List.of("import agh.edu.zeuspol.drools.RuleStats;");
  }
}
