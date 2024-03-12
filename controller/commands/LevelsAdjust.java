package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The LevelsAdjust class represents a command that performs a level-adjust operation on an image.
 */
public class LevelsAdjust extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 5) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    int b = Integer.parseInt(temp[0]);
    int m = Integer.parseInt(temp[1]);
    int w = Integer.parseInt(temp[2]);
    String src = temp[3];
    String dest = temp[4];
    model.levelAdjust(b, m, w, src, dest);
  }
}

