package agh.edu.zeuspol;

import agh.edu.zeuspol.drools.DroolsClass;
import io.github.hephaestusmetrics.model.metrics.Metric;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DroolsTest {

    @Test
    public void droolsTest(){
        DroolsClass droolsClass = new DroolsClass("ZeusSession");

        Metric m = Mockito.mock(Metric.class);
        Mockito.when(m.getValue()).thenReturn(0.7);
        Mockito.when(m.getName()).thenReturn("container_cpu_usage_seconds_total");

        droolsClass.fire(m);
    }
}
