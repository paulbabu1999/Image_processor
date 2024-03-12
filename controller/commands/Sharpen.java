package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Sharpen class represents a command that performs a sharpen operation on an image.
 */
public class Sharpen extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.sharpen(images[0], images[1]);
  }
}
