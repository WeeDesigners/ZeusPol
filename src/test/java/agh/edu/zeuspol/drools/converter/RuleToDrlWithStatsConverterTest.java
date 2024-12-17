package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import org.junit.jupiter.api.Test;

public class RuleToDrlWithStatsConverterTest {

  @Test
  public void test() {
    Params params = new Params();
    params.put("namespace", "test-app");
    params.put("deploymentName", "test-app");
    params.put("containerName", "test-app");
    params.put("limitsCpu", "2");
    params.put("limitsMemory", "800Mi");
    params.put("requestsCpu", "2");
    params.put("requestsMemory", "800Mi");

    Action action = new Action("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction", params);
    PolicyRule rule = new PolicyRule(1, "ScaleKubernetesRule", action);
    rule.addCondition(new Condition("CPU", RelationType.GT, 0.5));

    RuleToDrlWithStatsConverter converter = new RuleToDrlWithStatsConverter();

    System.out.println(converter.convert(rule).getFileContent());
  }
}
