package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.drools.DroolsClass;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DroolsTest {

    @Test
    public void droolsTest(){
        DroolsClass droolsClass = new DroolsClass("src/test/resources/drools");

        droolsClass.fire(Mockito.mock(Metric.class));
    }
}
