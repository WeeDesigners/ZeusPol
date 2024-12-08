package agh.edu.zeuspol.datastructures.types.attributes;

public enum Action {
  DEFAULT {
    @Override
    public void request(Params params) {
      System.out.println("Default action - requested");
    }

    @Override
    public ThemisActionTemplate getActionTemplate() {
      return null;
    }
  },
  ACTION_1 {
    @Override
    public void request(Params params) {
      // TODO
      System.out.println("Action 1 - requested");
    }

    @Override
    public ThemisActionTemplate getActionTemplate() {
      return null;
    }
  },
  ACTION_2 {
    @Override
    public void request(Params params) {
      // TODO
      System.out.println("Action 2 - requested");
    }

    @Override
    public ThemisActionTemplate getActionTemplate() {
      return null;
    }
  },

  KubernetesChangeResourcesOfContainerWithinDeploymentAction {
    @Override
    public void request(Params params) {
      // TODO
      System.out.println("Action 2 - requested");
    }

    private final ThemisActionTemplate actionTemplate =
        new ThemisActionTemplate(
            "kubernetes",
            "ChangeResourcesOfContainerWithinDeploymentAction",
            new String[] {},
            new String[] {
              "namespace",
              "deploymentName",
              "containerName",
              "limitsCpu",
              "limitsMemory",
              "requestsCpu",
              "requestsMemory"
            });

    @Override
    public ThemisActionTemplate getActionTemplate() {
      return actionTemplate;
    }
  },
  ;

  public abstract void request(Params params);

  public abstract ThemisActionTemplate getActionTemplate();
}
