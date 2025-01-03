package agh.edu.zeuspol.datastructures.storage;

import agh.edu.zeuspol.datastructures.types.SlaRule;
import agh.edu.zeuspol.drools.RuleStats;

import java.util.ArrayList;
import java.util.List;

public class Slas {

    private static Slas instance = new Slas();

    private List<Sla> slaList = new ArrayList<>();
    private List<RuleStats> rulesStatsList = new ArrayList<>();

    private Slas() {}

    public static Slas newInstance() {
        instance = new Slas();
        return instance;
    }

    public static Slas getInstance() {
        if (instance == null){
            instance = new Slas();
        }
        return instance;
    }

    public void addSla(Sla newSla) {
        slaList.add(newSla);
        for (SlaRule slaRule: newSla.getRules()){
            rulesStatsList.add(new RuleStats(slaRule));
        }
    }

    public void addSlaList(List<Sla> slaList){
        for (Sla sla: slaList){
            this.addSla(sla);
        }
    }

    public List<Sla> getSlaList() {
        return slaList;
    }

    public List<RuleStats> getRulesStats() {
        return this.rulesStatsList;
    }

}
