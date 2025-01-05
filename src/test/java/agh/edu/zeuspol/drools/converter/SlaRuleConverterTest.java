package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.datastructures.storage.Policies;
import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.storage.Slas;
import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.DrlRuleExecutor;
import agh.edu.zeuspol.drools.DrlStringFile;
import agh.edu.zeuspol.drools.DynamicDrlBuilder;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SlaRuleConverterTest {

    @BeforeAll
    public static void setupZeuspol() {
      ZeuspolApplication.loadZeuspolContext(new String[0]);
    }

    @Test
    public void test() {

      SlaRuleToDrlConverter converter = new SlaRuleToDrlConverter();

      SlaRule slaRule = new SlaRule(2, ValueType.AVAILABILITY);

      slaRule.addCondition(new Condition("CPU", RelationType.LT, 0.5));

      Sla sla = new Sla(1, "1", null, SlaType.SAAS, List.of(slaRule));

      Slas.getInstance().addSla(sla);

      DrlStringFile file = converter.convert(sla, slaRule);

      System.out.println(file.getFileContent());

      DynamicDrlBuilder dynamicDrlBuilder = new DynamicDrlBuilder();
      dynamicDrlBuilder.addFile(file);

      DrlRuleExecutor executor = dynamicDrlBuilder.build();

      Metric metric = new Metric("CPU", null, Map.of("a", "a"), 1, 0.6);

      executor.fireRules(List.of(metric, Slas.getInstance().getRulesStats()));
    }

    @Test
    public void test2() {

      SlaRule slaRule = new SlaRule(2, ValueType.AVAILABILITY);
      slaRule.addCondition(new Condition("CPU", RelationType.LT, 0.5));
      Sla sla = new Sla(1, "1", null, SlaType.SAAS, List.of(slaRule));
      Slas.getInstance().addSla(sla);


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
      l.addAll(Slas.getInstance().getRulesStats());

      executor.fireRules(l);

      dynamicDrlBuilder.addFile(fileSla);

      executor = dynamicDrlBuilder.build();
      executor.fireRules(l);
    }
}
