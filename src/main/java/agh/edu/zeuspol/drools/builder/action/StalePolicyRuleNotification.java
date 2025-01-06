package agh.edu.zeuspol.drools.builder.action;

public class StalePolicyRuleNotification {

    public static void sendNotification(long ruleId, String ruleName, String ruleConditions, String ruleAction) {

        String msg =
                "Metrics that trigger this policy does not improve:\n" +
                "Policy id: %s\n".formatted(ruleId) +
                "Policy name: %s\n".formatted(ruleName)+
                "Conditions: %s\n".formatted(ruleConditions) +
                "Action: %s\n".formatted(ruleAction);

        System.out.println(msg);
    }
}
