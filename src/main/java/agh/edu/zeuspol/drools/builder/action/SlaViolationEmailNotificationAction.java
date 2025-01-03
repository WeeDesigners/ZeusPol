package agh.edu.zeuspol.drools.builder.action;

import agh.edu.zeuspol.ZeuspolApplication;
import agh.edu.zeuspol.services.OnetEmailService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SlaViolationEmailNotificationAction {

  public static List<String> sendTo = List.of("pepesob2@gmail.com");
  private static OnetEmailService emailService;

  private static LocalDateTime lastSent = LocalDateTime.MIN;

  public static void sendNotification(long slaId, String clientId, String applicationId, String slaRuleString) {
    setupEmailService();
    String info =
        "SLA WAS VIOLATED!\n"
            + "SlaId: "
            + slaId
            + "\n"
            + "ClientId: "
            + clientId
            + "\n"
            + "ApplicationId: "
            + applicationId
            + "\n"
            + "Sla rule: "
            + slaRuleString
            + "\n";

    try {
      Duration duration = Duration.between(lastSent, LocalDateTime.now());
      if (duration.toMinutes() > 1){
        for(String receiver: sendTo) {
          emailService.sendSimpleEmail(receiver, "SLA VIOLATION ID: " + slaId, info);
          System.out.println("Notification sent to: " + receiver);
        }
        lastSent = LocalDateTime.now();
      }
    } catch (Exception e) {
      System.out.println("Could not send notification");
    }
    System.out.println(info);
  }

  private static void setupEmailService(){
    if (emailService == null){
      emailService = ZeuspolApplication.getContext().getBean(OnetEmailService.class);
    }
  }
}
