package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The IntensityComponent class represents a command that performs an intensity-component operation
 * on an image.
 */
public class IntensityComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.intensityComponent(images[0], images[1]);
  }
}


