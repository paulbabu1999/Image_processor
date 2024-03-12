package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The RGBSplit class represents a command that performs a RGBSplit operation on an image.
 */
public class RGBSplit extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 4) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    String src = temp[0];
    model.rgbSplit(src, new String[]{temp[1], temp[2], temp[3]});
  }
}
