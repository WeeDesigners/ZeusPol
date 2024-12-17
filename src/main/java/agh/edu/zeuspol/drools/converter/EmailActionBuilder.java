package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.storage.Sla;
import agh.edu.zeuspol.datastructures.types.SlaRule;


import java.util.List;

public class EmailActionBuilder {

    private List<String> imports = List.of("import agh.edu.zeuspol.services.SlaViolationEmailNotification;");
    public String buildEmailAction(Sla sla, SlaRule slaRule) {
        return "SlaViolationEmailNotification.sendNotification(" + sla.id + "," + "\"" + sla.getClientId() + "\"" +  "," + sla.getApplicationId() + "," + "\"" + slaRule.toString() + "\"" + ");";
    }

    public List<String> importsNeeded() {
        return this.imports;
    }
}
