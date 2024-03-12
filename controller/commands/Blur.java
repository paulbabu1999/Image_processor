package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Blur class represents a command that performs a blur operation on an image.
 */
public class Blur extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.blur(images[0], images[1]);
  }
}
