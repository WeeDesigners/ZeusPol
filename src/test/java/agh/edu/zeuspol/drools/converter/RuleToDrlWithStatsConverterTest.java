package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RuleToDrlWithStatsConverterTest {

  @Test
  public void test() {
    Params b = new Params();

    b.put("actionName", "ChangeResourcesOfContainerWithinDeploymentAction");
    b.put("collectionName", "kubernetes");
    b.put("namespace", "test-app");
    b.put("deploymentName", "test-app");
    b.put("containerName", "test-app");
    b.put("limitsCpu", "2");
    b.put("limitsMemory", "800Mi");
    b.put("requestsCpu", "2");
    b.put("requestsMemory", "800Mi");

    PolicyRule pRule =
        new PolicyRule(
            RuleAttribute.RESOURCE,
            RuleSubject.CPU,
            List.of(10),
            UnitType.PERCENT,
            RelationType.GT,
            Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction,
            b);

    RuleToDrlWithStatsConverter converter = new RuleToDrlWithStatsConverter();

    System.out.println(converter.convert(pRule).getFileContent());
  }
}
