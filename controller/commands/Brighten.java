package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Brighten class represents a command that performs a brighten operation on an image.
 */
public class Brighten extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 3) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    float factor = Float.parseFloat(temp[0]);
    String src = temp[1];
    String dest = temp[2];
    model.brighten(factor, src, dest);
  }
}


