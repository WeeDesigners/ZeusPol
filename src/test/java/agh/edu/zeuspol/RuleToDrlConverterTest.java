package agh.edu.zeuspol;


import agh.edu.zeuspol.datastructures.types.PolicyRule;
import agh.edu.zeuspol.datastructures.types.attributes.*;
import agh.edu.zeuspol.drools.*;
import agh.edu.zeuspol.drools.converter.CurlThemisActionBuilder;
import agh.edu.zeuspol.drools.converter.RuleToDrlConverter;
import agh.edu.zeuspol.drools.converter.ThemisActionBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;


public class RuleToDrlConverterTest {

    @Test
    public void tempConverterTest() {
        ThemisActionBuilder b = new CurlThemisActionBuilder();

        b.setActionName("ChangeResourcesOfContainerWithinDeploymentAction");
        b.setCollectionName("Kubernetes");
        b.setAction(Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction);
        b.addParam("namespace", "test-app");
        b.addParam("deploymentName", "test-app");
        b.addParam("containerName", "test-app");
        b.addParam("limitsCpu", "2");
        b.addParam("limitsMemory", "800Mi");
        b.addParam("requestsCpu", "2");
        b.addParam("requestsMemory", "800Mi");

        System.out.println(b.buildThemisAction());
    }

    @Test
    public void test2() {
        String s =
                """
                rule "memory-usage-above-20prv  "
                    when
                    then
                        System.out.println("curl -X POST http://themis-executor.themis-executor:8080/execute -H \\"Content-Type: application/json\\" -d '{\\"collectionName\\": \\"Kubernetes\\",\\"actionName\\": \\"ChangeResourcesOfContainerWithinDeploymentAction\\",\\"params\\": {\\"hey2\\": \\"hi2\\",\\"hey3\\": \\"hi3\\",\\"hey\\": \\"hi\\"}}'");
                end
                """;

        DynamicDrlBuilder builder = new DynamicDrlBuilder();

        builder.addFile("test/test.drl", s);

        builder.build().fireRules(null);
    }

    @Test
    public void test3() {
        RuleToDrlConverter converter = new RuleToDrlConverter();

        Params b = new Params();
        b.put("actionName","ChangeResourcesOfContainerWithinDeploymentAction");
        b.put("collectionName","kubernetes");
        b.put("namespace", "test-app");
        b.put("deploymentName", "test-app");
        b.put("containerName", "test-app");
        b.put("limitsCpu", "2");
        b.put("limitsMemory", "800Mi");
        b.put("requestsCpu", "2");
        b.put("requestsMemory", "800Mi");

        PolicyRule rule = new PolicyRule(
                RuleAttribute.RESOURCE,
                RuleSubject.CPU,
                List.of(10),
                UnitType.PERCENT,
                RelationType.GT,
                Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction,
                b
        );
        DrlStringFile s = converter.convert(rule);
        System.out.println(s);

        DynamicDrlBuilder builder = new DynamicDrlBuilder();

        builder.addFile(s);

        DrlRuleExecutor executor =  builder.build();
//        executor.fireRules(null);
    }
}
