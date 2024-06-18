package agh.edu.zeuspol;

import agh.edu.zeuspol.checker.SlaViolationChecker;
import agh.edu.zeuspol.datastructures.*;
import agh.edu.zeuspol.datastructures.storages.Sla;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SlaViolationCheckerTests {


    private final SlaViolationChecker checker = new SlaViolationChecker();

    @Test
    public void testCheck_NoViolation() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.EQ);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.EQ);

        assertFalse(checker.check(sla, rule));
    }

    @Test
    public void testCheck_ViolationDifferentUnits() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.EQ);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.NUMBER, ActionType.EQ);

        assertThrows(IllegalArgumentException.class, () -> checker.check(sla, rule));
    }

    @Test
    public void testCheck_ViolationDifferentActionTypes() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.LT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(11), UnitType.BYTE, ActionType.GT);

        assertTrue(checker.check(sla, rule));
    }

    @Test
    public void testCheck_BetweenNoViolation() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(5, 15), UnitType.BYTE, ActionType.BT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(6, 14), UnitType.BYTE, ActionType.BT);

        assertFalse(checker.check(sla, rule));
    }

    @Test
    public void testCheck_BetweenViolation() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(5, 15), UnitType.BYTE, ActionType.BT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(16, 20), UnitType.BYTE, ActionType.BT);

        assertTrue(checker.check(sla, rule));
    }

    @Test
    public void testCheck_MixedActionsNoViolation() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.GT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(11, 20), UnitType.BYTE, ActionType.BT);

        assertFalse(checker.check(sla, rule));
    }

    @Test
    public void testCheck_MixedActionsViolation() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Collections.singletonList(10), UnitType.BYTE, ActionType.GT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(5, 8), UnitType.BYTE, ActionType.BT);

        assertTrue(checker.check(sla, rule));
    }

    @Test
    public void testCheck_MixedActionsNoViolation2() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(10, 15), UnitType.BYTE, ActionType.BT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, List.of(12), UnitType.BYTE, ActionType.GT);

        assertFalse(checker.check(sla, rule));
    }

    @Test
    public void testCheck_MixedActionsViolation2() {
        Rule slaRule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, Arrays.asList(10, 15), UnitType.BYTE, ActionType.BT);
        Sla sla = new Sla(Collections.singletonList(slaRule), Collections.emptyList());

        Rule rule = new Rule(RuleAttribute.UPTIME, RuleSubject.CPU, List.of(16), UnitType.BYTE, ActionType.GT);

        assertTrue(checker.check(sla, rule));
    }
}
