package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The ColorCorrect class represents a command that performs a color-correct operation on an image.
 */
public class ColorCorrect extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] images = componentHelper(params);
    model.colorCorrect(images[0], images[1]);
  }
}
