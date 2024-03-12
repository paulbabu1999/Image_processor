package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The LumaComponent class represents a command that performs a luma-component operation on an
 * image.
 */
public class LumaComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.lumaComponent(images[0], images[1]);
  }
}