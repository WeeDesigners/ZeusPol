package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.exceptions.InvalidThemisActionException;
import agh.edu.zeuspol.exceptions.NoThemisActionTemplateFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThemisActionValidatorTest {

    @Test
    public void validAction1Test() throws NoThemisActionTemplateFoundException, InvalidThemisActionException {
        ThemisActionBuilder b = new CurlThemisActionBuilder();

        b.setActionName("ChangeResourcesOfContainerWithinDeploymentAction");
        b.setCollectionName("kubernetes");
        b.setAction(Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction);
        b.addParam("namespace", "test-app");
        b.addParam("deploymentName", "test-app");
        b.addParam("containerName", "test-app");
        b.addParam("limitsCpu", "2");
        b.addParam("limitsMemory", "800Mi");
        b.addParam("requestsCpu", "2");
        b.addParam("requestsMemory", "800Mi");

        ThemisActionValidator.validate(Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction, b.getParams());
    }


    @Test
    public void invalidAction1Test() {
        ThemisActionBuilder b = new CurlThemisActionBuilder();

        b.setActionName("ChangeResourcesOfContainerWithinDeploymentAction");
        b.setCollectionName("Kubernetes");
        b.setAction(Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction);
        b.addParam("namespace", "test-app");
        b.addParam("deploymentName", "test-app");
        b.addParam("containerName", "test-app");
        b.addParam("limitsCpu", "2");
        b.addParam("limitsMemory", "800Mi");

        Assertions.assertThrows(InvalidThemisActionException.class, () ->ThemisActionValidator.validate(Action.KubernetesChangeResourcesOfContainerWithinDeploymentAction, b.getParams()));
    }


    @Test
    public void throwsNoThemisActionTemplateFoundExceptionTest() {
        ThemisActionBuilder b = new CurlThemisActionBuilder();

        b.setActionName("RandomActionName");
        b.setCollectionName("RandomCollectionName");
        b.setAction(Action.DEFAULT);

        Assertions.assertThrows(NoThemisActionTemplateFoundException.class, () -> {ThemisActionValidator.validate(Action.DEFAULT, b.getParams());});
    }
}
