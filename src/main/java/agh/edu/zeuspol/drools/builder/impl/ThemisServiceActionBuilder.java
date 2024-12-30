package agh.edu.zeuspol.drools.builder.impl;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.drools.builder.base.DrlActionBuilder;
import java.util.List;
import java.util.Map;

public class ThemisServiceActionBuilder extends DrlActionBuilder {

  private Action action;

  public ThemisServiceActionBuilder(Action action) {
    this.action = action;
  }

  @Override
  public String build() {

    return """
ThemisService themisService = ZeuspolApplication.getContext().getBean(ThemisService.class);
"""
        + this.paramsString(this.action.params)
        + """
          ExecutionRequest executionRequest = new ExecutionRequest("%s", "%s", params);
          System.out.println(themisService.executeAction(executionRequest));
          """
            .formatted(this.action.collectionName, this.action.actionName);
  }

  @Override
  public List<String> imports() {
    return List.of(
        "import agh.edu.zeuspol.services.ThemisService;",
        "import agh.edu.zeuspol.datastructures.types.attributes.ExecutionRequest;",
        "import agh.edu.zeuspol.datastructures.types.attributes.Params;",
        "import agh.edu.zeuspol.ZeuspolApplication;");
  }

  private String paramsString(Params params) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Params params = new Params();\n");
    for (Map.Entry<String, String> param : params.entrySet()) {
      stringBuilder.append(
          """
          params.put("%s", "%s");
          """
              .formatted(param.getKey(), param.getValue()));
    }
    return stringBuilder.toString();
  }
}
