package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The RedComponent class represents a command that performs a red-component operation on an image.
 */
public class RedComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.redComponent(images[0], images[1]);
  }
}