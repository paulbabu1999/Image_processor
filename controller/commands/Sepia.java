package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Sepia class represents a command that performs a sepia operation on an image.
 */
public class Sepia extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.sepia(images[0], images[1]);
  }
}
