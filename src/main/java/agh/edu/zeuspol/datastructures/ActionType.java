package agh.edu.zeuspol.datastructures;

import java.util.List;

public enum ActionType {

  // to execute functions use:
  // ActionType.TYPE.apply(arg1, arg2);

  GT {
    @Override
    public boolean apply(List<Number> list, Number n2) {
      Number n1 = list.get(0);
      return (n2.doubleValue() > n1.doubleValue());
    }
  },
  LT {
    @Override
    public boolean apply(List<Number> list, Number n2) {
      Number n1 = list.get(0);
      return (n2.doubleValue() < n1.doubleValue());
    }
  },
  EQ {
    @Override
    public boolean apply(List<Number> list, Number n2) {
      Number n1 = list.get(0);
      return n1.doubleValue() == n2.doubleValue();
    }
  },
  BT {
    @Override
    public boolean apply(List<Number> list, Number n2) {
      double a = list.get(0).doubleValue();
      double b = list.get(1).doubleValue();

      // swap
      if (a > b) {
        // I hope it works...
        a = a + b;
        b = a - b;
        a = a - b;
      }
      return (n2.doubleValue() > a) && (n2.doubleValue() < b);
    }
  },
  ;

  // etc.

  public abstract boolean apply(List<Number> list, Number n2);
}
