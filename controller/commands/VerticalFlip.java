package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The VerticalFlip class represents a command that performs a vertical-flip operation on an image.
 */
public class VerticalFlip extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.verticalFlip(images[0], images[1]);
  }
}
