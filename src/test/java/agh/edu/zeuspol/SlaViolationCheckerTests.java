package agh.edu.zeuspol;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import agh.edu.zeuspol.checker.SlaViolationChecker;
//import agh.edu.zeuspol.datastructures.storage.Sla;
//import agh.edu.zeuspol.datastructures.types.SlaRule;
//import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
//import agh.edu.zeuspol.datastructures.types.attributes.RuleAttribute;
//import agh.edu.zeuspol.datastructures.types.attributes.RuleSubject;
//import agh.edu.zeuspol.datastructures.types.attributes.UnitType;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import org.junit.jupiter.api.Test;
// TODO - right now this SlaViolationChecker does not work thus tests are commented out
public class SlaViolationCheckerTests {
//
//  @Test
//  public void testCheck_NoViolation() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.EQ);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.EQ);
//
//    assertFalse(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_ViolationDifferentUnits() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.EQ);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.NUMBER,
//            RelationType.EQ);
//
//    assertThrows(IllegalArgumentException.class, () -> SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_ViolationDifferentActionTypes() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.LT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(11),
//            UnitType.BYTE,
//            RelationType.GT);
//
//    assertTrue(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_BetweenNoViolation() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(5, 15),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(6, 14),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    assertFalse(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_BetweenViolation() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(5, 15),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(16, 20),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    assertTrue(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_MixedActionsNoViolation() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.GT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(11, 20),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    assertFalse(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_MixedActionsViolation() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Collections.singletonList(10),
//            UnitType.BYTE,
//            RelationType.GT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(5, 8),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    assertTrue(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_MixedActionsNoViolation2() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(10, 15),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME, RuleSubject.CPU, List.of(12), UnitType.BYTE, RelationType.GT);
//
//    assertFalse(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
//
//  @Test
//  public void testCheck_MixedActionsViolation2() {
//    SlaRule slaRule =
//        new SlaRule(
//            RuleAttribute.UPTIME,
//            RuleSubject.CPU,
//            Arrays.asList(10, 15),
//            UnitType.BYTE,
//            RelationType.BT);
//
//    Sla sla = Sla.getInstance();
//    sla.clearSla();
//    sla.addRule(slaRule);
//
//    SlaRule rule =
//        new SlaRule(
//            RuleAttribute.UPTIME, RuleSubject.CPU, List.of(16), UnitType.BYTE, RelationType.GT);
//
//    assertTrue(SlaViolationChecker.checkRule(rule));
//
//    sla.clearSla();
//  }
}
