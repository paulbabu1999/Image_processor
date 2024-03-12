package controller.commands;

import java.io.IOException;
import java.util.Arrays;
import model.IModel;

/**
 * The Chop class represents a command that performs a chop operation on an image.
 */
public class Chop extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] tokens = params.split(" ");
    //Percentage of split line
    float percentage = Float.parseFloat(tokens[tokens.length - 1]);
    String[] args = Arrays.copyOfRange(tokens, 0, tokens.length - 2);
    String[] images = componentHelper(String.join(" ", args));
    model.chop(percentage, images[0], images[1]);
  }
}








