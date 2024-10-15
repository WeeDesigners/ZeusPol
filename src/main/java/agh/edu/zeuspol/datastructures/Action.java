package agh.edu.zeuspol.datastructures;

import java.util.Map;

public enum Action {

    DEFAULT {
        @Override
        public void request(Map<String, String> params) {
            System.out.println("Default action - requested");
        }
    },
    ACTION_1 {
        @Override
        public void request(Map<String, String> params) {
            //TODO
            System.out.println("Action 1 - requested");
        }
    },
    ACTION_2 {
        @Override
        public void request(Map<String, String> params) {
            //TODO
            System.out.println("Action 2 - requested");
        }
    },
    ;

    public abstract void request(Map<String, String> params);
}
