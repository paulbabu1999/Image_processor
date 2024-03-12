package controller.commands;

import java.io.IOException;
import model.IModel;

/**
 * The Command interface represents a command that can be executed on images.
 */
public interface Command {

  void runCommand(String params, IModel model)
      throws IOException, InstantiationException, IllegalAccessException;
}
