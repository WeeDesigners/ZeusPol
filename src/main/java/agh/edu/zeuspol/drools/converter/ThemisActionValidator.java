package agh.edu.zeuspol.drools.converter;

import agh.edu.zeuspol.datastructures.types.attributes.Action;
import agh.edu.zeuspol.datastructures.types.attributes.Params;
import agh.edu.zeuspol.exceptions.InvalidThemisActionException;
import agh.edu.zeuspol.exceptions.NoThemisActionTemplateFoundException;
import java.util.Arrays;
import java.util.Objects;

public class ThemisActionValidator {

  //    Method that validates if instance of ThemisActionBuilder contains proper parameters for
  // given action
  //    If ThemisActionBuilder has valid params for Action -> method do not throw any exceptions;
  //    If ThemisActionBuilder has invalid params for Action -> throws InvalidThemisActionException
  // with message what was wrong;
  //    If no template exist for given Action -> throws NoThemisActionTemplateFoundException;
  public static void validate(Action action, Params params)
      throws NoThemisActionTemplateFoundException, InvalidThemisActionException {
    ThemisActionTemplate template = action.getActionTemplate();
    if (template == null) {
      throw new NoThemisActionTemplateFoundException();
    } else {
      if (!Objects.equals(params.get("collectionName"), template.getCollectionName())) {
        throw new InvalidThemisActionException("Invalid collection name");
      }
      if (!Objects.equals(params.get("actionName"), template.getActionName())) {
        throw new InvalidThemisActionException("Invalid action name");
      }

      String[] requiredParams = template.getRequiredParams();
      String[] optionalParams = template.getOptionalParams();
      for (String requiredParam : requiredParams) {
        if (!params.containsKey(requiredParam)) {
          throw new InvalidThemisActionException("Missing required parameter: " + requiredParam);
        }
      }
      for (String param : params.keySet()) {
        if (!(Arrays.asList(requiredParams).contains(param)
            || Arrays.asList(optionalParams).contains(param)
            || param.equals("collectionName")
            || param.equals("actionName"))) {
          throw new InvalidThemisActionException("Unrecognised param: " + param);
        }
      }
    }
  }
}
