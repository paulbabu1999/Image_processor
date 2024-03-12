package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The RGBCombine class represents a command that performs a RGBCombine operation on an image.
 */
public class RGBCombine extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 4) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    model.rgbCombine(new String[]{temp[1], temp[2], temp[3]}, temp[0]);
  }
}
