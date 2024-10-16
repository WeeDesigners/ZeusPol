package agh.edu.zeuspol.drools;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.exceptions.NoThemisActionTemplateFoundException;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;

public class ThemisActionValidator {

//    Method that validates if instance of ThemisActionBuilder contains proper parameters for given action
//    If ThemisActionBuilder has valid params for Action -> returns true;
//    If ThemisActionBuilder has valid params for Action -> returns false;
//    If no template exist for given Action -> throws NoThemisActionTemplateFoundException;
    public static boolean validate(Action action, ThemisActionBuilder actionBuilder) throws NoThemisActionTemplateFoundException {
        ThemisActionTemplate template = action.getActionTemplate();
        if (template == null) {
            throw new NoThemisActionTemplateFoundException();
        } else {
            Params params = actionBuilder.getParams();
            String[] requiredParams = template.getRequiredParams();
            String[] optionalParams = template.getOptionalParams();
            for (String requiredParam: requiredParams) {
                if (!params.containsKey(requiredParam)) {
                    return false;
                }
            }
            for (String param: params.keySet()) {
                if (!(Arrays.asList(requiredParams).contains(param) || Arrays.asList(optionalParams).contains(param))) {
                    return false;
                }
            }
        }

        return true;
    }
}


