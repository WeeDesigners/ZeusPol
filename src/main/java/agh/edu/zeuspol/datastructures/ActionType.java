package agh.edu.zeuspol.datastructures;

import java.util.List;

public enum ActionType {

    //to execute functions use:
    // ActionType.TYPE.apply(arg1, arg2);

    GT {
        @Override
        public boolean apply(List<Number> list, Number n2){
            Number n1 = list.get(0);
            return (n1.doubleValue() > n2.doubleValue());
        }
    },
    LT{
        @Override
        public boolean apply(List<Number> list, Number n2){
            Number n1 = list.get(0);
            return (n1.doubleValue() < n2.doubleValue());
        }
    },
    EQ{
        @Override
        public boolean apply(List<Number> list, Number n2){
            Number n1 = list.get(0);
            return n1.doubleValue() == n2.doubleValue();
        }
    },
    BT{
        @Override
        public boolean apply(List<Number> list, Number n2){
            Number a = list.get(0);
            Number b = list.get(1);
            return (n2.doubleValue() > a.doubleValue()) && (n2.doubleValue() < b.doubleValue());
        }
    },
    ;
    //etc.




    public abstract boolean apply(List<Number> list, Number n2);
}
