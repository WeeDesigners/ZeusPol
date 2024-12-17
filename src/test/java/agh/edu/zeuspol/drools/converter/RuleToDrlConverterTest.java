package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import org.junit.jupiter.api.Test;

public class RuleToDrlConverterTest {

  @Test
  public void doesConvertedRuleCompileTest() {
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

    RuleToDrlConverter converter = new RuleToDrlConverter(new CurlThemisActionBuilder());
    DrlStringFile drlStringFile = converter.convert(rule);
    DynamicDrlBuilder builder = new DynamicDrlBuilder();
    builder.addFile(drlStringFile);
    builder.build();

    System.out.println(converter.convert(rule).getFileContent());
  }

  @Test
  public void doesMultipleConditionsCompileTest() {
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
    rule.addCondition(new Condition("RAM", RelationType.GT, 16));
    rule.addCondition(new Condition("STORAGE", RelationType.GT, 256));

    RuleToDrlConverter converter = new RuleToDrlConverter(new CurlThemisActionBuilder());
    DrlStringFile drlStringFile = converter.convert(rule);
    DynamicDrlBuilder builder = new DynamicDrlBuilder();
    builder.addFile(drlStringFile);
    builder.build();

    System.out.println(converter.convert(rule).getFileContent());
  }
}
