package agh.edu.zeuspol.drools;

public class ThemisActionTemplate {
    private String collectionName;
    private String actionName;
    private String[] optionalParams;
    private String[] requiredParams;

    public ThemisActionTemplate(String collectionName, String actionName, String[] optionalParams, String[] requiredParams) {
        this.collectionName = collectionName;
        this.actionName = actionName;
        this.optionalParams = optionalParams;
        this.requiredParams = requiredParams;
    }

    public String getActionName() {
        return actionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String[] getOptionalParams() {
        return optionalParams;
    }

    public String[] getRequiredParams() {
        return requiredParams;
    }
}
