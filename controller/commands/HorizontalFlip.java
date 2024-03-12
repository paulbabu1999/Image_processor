package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The HorizontalFlip class represents a command that performs a horizontal flip operation on an
 * image.
 */
public class HorizontalFlip extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.horizontalFlip(images[0], images[1]);
  }
}
