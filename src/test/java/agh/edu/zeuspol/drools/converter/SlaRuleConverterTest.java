package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.DrlRuleExecutor;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import io.github.hephaestusmetrics.model.metrics.Metric;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SlaRuleConverterTest {

  @Test
  public void test() {
    SlaRuleToDrlConverter converter = new SlaRuleToDrlConverter();

    SlaRule slaRule = new SlaRule(2, ValueType.AVAILABILITY);

    slaRule.addCondition(new Condition("CPU", RelationType.LT, 0.5));

    Sla sla = new Sla(1, "1", null, SlaType.SAAS, List.of(slaRule));

    DrlStringFile file = converter.convert(sla, slaRule);

    System.out.println(file.getFileContent());

    DynamicDrlBuilder dynamicDrlBuilder = new DynamicDrlBuilder();
    dynamicDrlBuilder.addFile(file);

    DrlRuleExecutor executor = dynamicDrlBuilder.build();

    Metric metric = new Metric("CPU", null, Map.of("a", "a"), 1, 0.6);

    executor.fireRules(List.of(metric));
  }

  @Test
  public void test2() {

    SlaRule slaRule = new SlaRule(2, ValueType.AVAILABILITY);
    slaRule.addCondition(new Condition("CPU", RelationType.LT, 0.5));
    Sla sla = new Sla(1, "1", null, SlaType.SAAS, List.of(slaRule));

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

    Metric metric = new Metric("CPU", null, Map.of("a", "a"), 1, 0.6);

    Policies p = Policies.getInstance();
    p.addRule(rule);

    SlaRuleToDrlConverter slaRuleConverter = new SlaRuleToDrlConverter();
    PolicyRuleToDrlConverter policyConverter = new PolicyRuleToDrlConverter();

    DrlStringFile fileSla = slaRuleConverter.convert(sla, slaRule);
    DrlStringFile filePolicy = policyConverter.convert(rule);

    System.out.println(fileSla.getFileContent());
    System.out.println(filePolicy.getFileContent());

    DynamicDrlBuilder dynamicDrlBuilder = new DynamicDrlBuilder();
    dynamicDrlBuilder.addFile(filePolicy);

    DrlRuleExecutor executor = dynamicDrlBuilder.build();

    List<Object> l = new ArrayList<>();
    l.add(metric);
    l.addAll(Policies.getInstance().getRulesStatsList());

    executor.fireRules(l);

    dynamicDrlBuilder.addFile(fileSla);
    executor = dynamicDrlBuilder.build();

    executor.fireRules(l);
  }
}
