package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The ValueComponent class represents a command that performs a value-component operation on an
 * image.
 */
public class ValueComponent extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.valueComponent(images[0], images[1]);
  }
}
