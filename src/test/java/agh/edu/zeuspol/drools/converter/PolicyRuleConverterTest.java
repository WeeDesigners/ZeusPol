package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Condition;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
import agh.edu.zeuspol.drools.DrlRuleExecutor;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolicyRuleConverterTest {
  @BeforeAll
  public static void setupZeuspol() {
    ZeuspolApplication.loadZeuspolContext(new String[0]);
  }

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
    params.put("specialcharacterstest", "hey\\nhello\\rletsseeifitworks\\t");

    Action action =
        new Action("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction", params);
    PolicyRule rule = new PolicyRule(1, "ScaleKubernetesRule", action);
    rule.addCondition(new Condition("CPU", RelationType.GT, 0.5));

    PolicyRuleToDrlConverter converter = new PolicyRuleToDrlConverter();
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

    Action action =
        new Action("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction", params);
    PolicyRule rule = new PolicyRule(1, "ScaleKubernetesRule", action);
    rule.addCondition(new Condition("CPU", RelationType.GT, 0.5));
    rule.addCondition(new Condition("RAM", RelationType.GT, 16));
    rule.addCondition(new Condition("STORAGE", RelationType.GT, 256));

    PolicyRuleToDrlConverter converter = new PolicyRuleToDrlConverter();
    DrlStringFile drlStringFile = converter.convert(rule);
    DynamicDrlBuilder builder = new DynamicDrlBuilder();
    builder.addFile(drlStringFile);
    builder.build();

    System.out.println(converter.convert(rule).getFileContent());
  }

  @Test
  public void doesNewLogicWork() {
    Params params = new Params();
    params.put("namespace", "test-app");
    params.put("deploymentName", "test-app");
    params.put("containerName", "test-app");
    params.put("limitsCpu", "2");
    params.put("limitsMemory", "800Mi");
    params.put("requestsCpu", "2");
    params.put("requestsMemory", "800Mi");

    Action action =
            new Action("kubernetes", "ChangeResourcesOfContainerWithinDeploymentAction", params);
    PolicyRule rule = new PolicyRule(1, "ScaleKubernetesRule", action, 2, 0);
    rule.addCondition(new Condition("CPU", RelationType.GT, 0.5));
    rule.addCondition(new Condition("RAM", RelationType.GT, 16));
    rule.addCondition(new Condition("STORAGE", RelationType.GT, 256));

    Policies.newInstance().addRule(rule);

    Metric metric0 = new Metric("CPU", null, Map.of("a", "a"), 1, 1.0);
    Metric metric1 = new Metric("RAM", null, Map.of("a", "a"), 1, 17.0);
    Metric metric2 = new Metric("STORAGE", null, Map.of("a", "a"), 1, 257.0);

    PolicyRuleToDrl2Converter converter = new PolicyRuleToDrl2Converter();
    DrlStringFile drlStringFile = converter.convert(Policies.getInstance().getRules().get(0));
    DynamicDrlBuilder builder = new DynamicDrlBuilder();
    builder.addFile(drlStringFile);
    DrlRuleExecutor executor = builder.build();
    System.out.println(converter.convert(rule).getFileContent());

    //    Here we are making policy not improving state of the system
    List<Object> facts = new ArrayList<>();

    facts.add(metric0);
    facts.add(metric1);
    facts.add(metric2);
    facts.addAll(Policies.getInstance().getRulesStatsList());

    executor.fireRules(facts);
    executor.fireRules(facts);
    executor.fireRules(facts);



    //    Here we are improving state of the system so the notification should disappear
    metric0 = new Metric("CPU", null, Map.of("a", "a"), 1, 0.9);
    metric1 = new Metric("RAM", null, Map.of("a", "a"), 1, 16.9);
    metric2 = new Metric("STORAGE", null, Map.of("a", "a"), 1, 256.9);

    facts = new ArrayList<>();
    facts.add(metric0);
    facts.add(metric1);
    facts.add(metric2);
    facts.addAll(Policies.getInstance().getRulesStatsList());

    executor.fireRules(facts);
    executor.fireRules(facts);

//    Here notification should appear again
    executor.fireRules(facts);
  }
}
