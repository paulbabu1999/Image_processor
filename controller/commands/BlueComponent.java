package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The BlueComponent class represents a command that performs a blue-component operation on an
 * image.
 */
public class BlueComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.blueComponent(images[0], images[1]);
  }
}
