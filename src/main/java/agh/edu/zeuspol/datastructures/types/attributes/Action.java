package agh.edu.zeuspol.datastructures.types.attributes;

public enum Action {
  DEFAULT {
    @Override
    public void request(Params params) {
      System.out.println("Default action - requested");
    }
  },
  ACTION_1 {
    @Override
    public void request(Params params) {
      // TODO
      System.out.println("Action 1 - requested");
    }
  },
  ACTION_2 {
    @Override
    public void request(Params params) {
      // TODO
      System.out.println("Action 2 - requested");
    }
  },
  ;

  public abstract void request(Params params);
}
