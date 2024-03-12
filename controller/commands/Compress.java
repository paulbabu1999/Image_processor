package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Compress class represents a command that performs a compress operation on an image.
 */
public class Compress extends AbstractCommand {

  @Override
  public void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] temp = params.split(" ");
    if (temp.length != 3) {
      throw new IllegalArgumentException("Illegal Number of Parameters " + params);
    }
    float percentage = Float.parseFloat(temp[0]);
    String original_image = temp[1];
    String compressed_image = temp[2];
    model.compress(percentage, original_image, compressed_image);
  }
}



