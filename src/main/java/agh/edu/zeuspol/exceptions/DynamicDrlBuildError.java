package agh.edu.zeuspol.exceptions;

import java.util.List;
import org.kie.api.builder.Message;

public class DynamicDrlBuildError extends RuntimeException {
  public DynamicDrlBuildError(List<Message> messages) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < messages.size(); i++) {
      sb.append("-----------------------------------------------------\n");
      sb.append("Error ").append(i + 1).append(": \n");
      sb.append(messages.get(i).toString());
      sb.append("-----------------------------------------------------\n");
    }
  }
}
