package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Underlay class represents a command that performs an underlay on an image.
 */
public class Underlay extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.underlay(images[1], images[0], images[0]);
  }
}
