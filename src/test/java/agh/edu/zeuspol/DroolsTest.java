package agh.edu.zeuspol;

import agh.edu.zeuspol.datastructures.Rule;
import agh.edu.zeuspol.drools.DroolsClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DroolsTest {

    @Test
    public void droolsTest(){
        DroolsClass droolsClass = new DroolsClass("drools");

        droolsClass.fire(Mockito.mock(Rule.class));
    }
}
