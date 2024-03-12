package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Histogram class represents a command that performs a histogram operation on an image.
 */
public class Histogram extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.histogram(images[0], images[1]);
  }
}