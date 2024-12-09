package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RuleToDrlConverterTest {

  @Test
  public void doesConvertedRuleCompileTest() {
    ExecutionRequest executionRequest = new ExecutionRequest("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction");

    executionRequest.addParam("namespace", "test-app");
    executionRequest.addParam("deploymentName", "test-app");
    executionRequest.addParam("containerName", "test-app");
    executionRequest.addParam("limitsCpu", "2");
    executionRequest.addParam("limitsMemory", "800Mi");
    executionRequest.addParam("requestsCpu", "2");
    executionRequest.addParam("requestsMemory", "800Mi");


    PolicyRule pRule = new PolicyRule(1, "testName", "CPU", RelationType.GT, 0.5, executionRequest);


    RuleToDrlConverter converter = new RuleToDrlConverter(new CurlThemisActionBuilder());
    DrlStringFile drlStringFile = converter.convert(pRule);
    DynamicDrlBuilder builder = new DynamicDrlBuilder();
    builder.addFile(drlStringFile);
    builder.build();


    System.out.println(converter.convert(pRule).getFileContent());

  }
}
