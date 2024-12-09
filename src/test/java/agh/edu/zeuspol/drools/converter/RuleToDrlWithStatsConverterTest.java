package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RuleToDrlWithStatsConverterTest {

  @Test
  public void test() {
    ExecutionRequest executionRequest = new ExecutionRequest("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction");

    executionRequest.addParam("namespace", "test-app");
    executionRequest.addParam("deploymentName", "test-app");
    executionRequest.addParam("containerName", "test-app");
    executionRequest.addParam("limitsCpu", "2");
    executionRequest.addParam("limitsMemory", "800Mi");
    executionRequest.addParam("requestsCpu", "2");
    executionRequest.addParam("requestsMemory", "800Mi");

    PolicyRule pRule = new PolicyRule(1, "testName", "CPU", RelationType.GT, 0.5, executionRequest);


    RuleToDrlWithStatsConverter converter = new RuleToDrlWithStatsConverter();

    System.out.println(converter.convert(pRule).getFileContent());
  }
}
