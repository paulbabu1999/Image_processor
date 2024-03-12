package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The GreenComponent class represents a command that performs a green-component operation on an
 * image.
 */
public class GreenComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.greenComponent(images[0], images[1]);
  }
}