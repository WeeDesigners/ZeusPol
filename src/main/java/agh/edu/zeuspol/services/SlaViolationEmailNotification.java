package agh.edu.zeuspol.services;

import agh.edu.zeuspol.datastructures.types.SlaRule;

import java.util.List;

public class SlaViolationEmailNotification {

    public static List<String> sendTo = List.of("temp@temp.com");


    public static void sendNotification(long slaId, String clientId, String applicationId, String slaRuleString) {

        String info = "SLA WAS VIOLATED!\n"
                + "SlaId: " + slaId + "\n"
                + "ClientId: " + clientId + "\n"
                + "ApplicationId: " + applicationId + "\n"
                + "Sla rule: " + slaRuleString + "\n";


        System.out.println("Notification sent to: " + sendTo);
        System.out.println(info);
    }
}
