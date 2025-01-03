package agh.edu.zeuspol;

import agh.edu.zeuspol.drools.builder.action.SlaViolationEmailNotificationAction;
import agh.edu.zeuspol.services.OnetEmailService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSendingTest {

    @BeforeAll
    public static void setupZeuspol() {
        ZeuspolApplication.loadZeuspolContext(new String[0]);
    }

    @Autowired
    OnetEmailService onetEmailService;

    @Test
    public void test() {
        try {
            onetEmailService.sendSimpleEmail("pepesob2@gmail.com", "Test", "This is test if it worksasdfasdfasfd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            SlaViolationEmailNotificationAction.sendNotification(1, "2", "2", "asdfasdfasdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
