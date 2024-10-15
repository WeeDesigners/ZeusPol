package agh.edu.zeuspol.datastructures;

import java.util.Map;

public enum Action {

    ACTION_1 {
        @Override
        public void execute(Map<String, String> params) {
            //TODO
            System.out.println("Action 1 - requested");
        }
    },
    ACTION_2 {
        @Override
        public void execute(Map<String, String> params) {
            //TODO
            System.out.println("Action 2 - requested");
        }
    },
    ;

    public abstract void execute(Map<String, String> params);
}
