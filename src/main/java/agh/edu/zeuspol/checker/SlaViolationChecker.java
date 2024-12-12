// package agh.edu.zeuspol.checker;
//
// import agh.edu.zeuspol.datastructures.storage.Sla;
// import agh.edu.zeuspol.datastructures.types.attributes.RelationType;
// import agh.edu.zeuspol.datastructures.types.base.Rule;
// import java.util.List;
//
//// TODO -> Currently outdated and not needed
//
// public class SlaViolationChecker {
//
//  public static boolean checkRules(List<Rule> rules) {
//    for (Rule rule : rules) {
//      if (!checkRule(rule)) {
//        return false;
//      }
//    }
//    return true;
//  }
//
//  public static boolean checkRule(Rule rule) {
//    Sla sla = Sla.getInstance();
//    for (Rule slaRule : sla.getRules()) {
//      if (slaRule.subject == rule.subject && slaRule.attribute == rule.attribute) {
//        if (slaRule.unit != rule.unit) {
//          throw new IllegalArgumentException();
//        }
//        if (slaRule.relation == RelationType.EQ || rule.relation == RelationType.EQ) {
//          if (slaRule.relation == RelationType.EQ && rule.relation == RelationType.EQ) {
//            RelationType action = slaRule.relation;
//            if (!action.apply(slaRule.value, rule.value.get(0))) {
//              return true;
//            }
//          } else {
//            return true;
//          }
//        } else if (slaRule.relation == RelationType.BT && rule.relation == RelationType.BT) {
//          if (!isBetween(slaRule.value, rule.value)) {
//            return true;
//          }
//        } else if (slaRule.relation == RelationType.BT) {
//          RelationType action = rule.relation;
//          Number n1 = slaRule.value.get(0);
//          Number n2 = slaRule.value.get(1);
//          if (!(action.apply(rule.value, n1) || action.apply(rule.value, n2))) {
//            return true;
//          }
//        } else if (rule.relation == RelationType.BT) {
//          RelationType action = slaRule.relation;
//          Number n1 = rule.value.get(0);
//          Number n2 = rule.value.get(1);
//          if (!(action.apply(slaRule.value, n1) && action.apply(slaRule.value, n2))) {
//            return true;
//          }
//        } else if (slaRule.relation != rule.relation) {
//          return true;
//        } else {
//          RelationType action = slaRule.relation;
//          if (action.apply(slaRule.value, rule.value.get(0))) {
//            return true;
//          }
//        }
//      }
//    }
//    return false;
//  }
//
//  private static boolean isBetween(List<Number> span1, List<Number> span2) {
//    if (span1.size() != 2 || span2.size() != 2) {
//      throw new RuntimeException("Wrong argument number in lists!");
//    }
//    double n1 = span1.get(0).doubleValue();
//    double n2 = span1.get(1).doubleValue();
//
//    double m1 = span2.get(0).doubleValue();
//    double m2 = span2.get(1).doubleValue();
//    return n1 <= m1 && n1 <= m2 && n2 >= m1 && n2 >= m2;
//  }
// }
