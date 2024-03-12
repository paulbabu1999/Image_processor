package controller;

import controller.commands.BlueComponent;
import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Chop;
import controller.commands.ColorCorrect;
import controller.commands.Command;
import controller.commands.Compress;
import controller.commands.GreenComponent;
import controller.commands.Histogram;
import controller.commands.HorizontalFlip;
import controller.commands.IntensityComponent;
import controller.commands.LevelsAdjust;
import controller.commands.Load;
import controller.commands.LumaComponent;
import controller.commands.RGBCombine;
import controller.commands.RGBSplit;
import controller.commands.RedComponent;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.Underlay;
import controller.commands.ValueComponent;
import controller.commands.VerticalFlip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import model.IModel;
import view.View;

/**
 * The `controller.IMEController` class is responsible for controlling the execution of image
 * processing commands. It handles user input, runs scripts, and manages image data.
 */
public class IMEController implements Controller {

  final Scanner inputSource;
  protected View view;
  protected IModel model;
  protected final Map<ImageCommandType, Command> commandMap = getCommands();
  private String input = "";

  /**
   * Constructs an `controller.IMEController` with the specified input source, view, and model
   * class.
   *
   * @param inputSource The source for user input.
   * @param view        The view for displaying information.
   * @param model       The model class for image processing.
   */
  public IMEController(Readable inputSource, View view,
      IModel model) {
    this.view = view;
    this.inputSource = new Scanner(inputSource);
    this.model = model;
  }

  public IMEController(View view,
      IModel model) {
    this.inputSource = null;
    this.view = view;
    this.model = model;


  }


  /**
   * Starts the image processing controller, allowing users to interact with the program.
   *
   * @throws IOException if an I/O error occurs during the execution.
   */
  @Override
  public void start(String[] args) throws IOException {
    if (args != null) {
      if (args.length == 1) {
        runScript(args[0]);
        return;
      } else if (args.length > 1) {
        try {
          throw new IllegalArgumentException("Illegal number of arguments, filepath expected");
        } catch (IllegalArgumentException e) {
          view.showError(e.getMessage());
        }
      }
    }

    while (!Objects.equals(input, "quit")) {

      updateView();
      try {
        if (input.equals("script")) {
          updateView();
          String filePath = input;
          runScript(filePath);
          continue;
        }
        if (Objects.equals(input, "quit")) {
          return;
        }

        runCommand(input);


      } catch (Exception e) {
        view.showError(String.valueOf(e));
      }
    }
  }

  private String readInput() {

    return inputSource.nextLine();
  }

  //Control view object
  private void updateView() throws IOException {
    view.printNextPrompt(Objects.equals(input, "script"));
    input = readInput();
  }

  private void runScript(String filePath) throws IOException {
    File file = new File(filePath);

    if (!file.isAbsolute()) {
      String currentDir = System.getProperty("user.dir");
      file = new File(currentDir, filePath);
    }
    int line_number = 0;
    filePath = file.getAbsolutePath();

    //Read script file one by one and call command parser
    // Error - arraylist.addFront - line and error or file couldn't be loaded
    try {
      if (!(new File(filePath).isFile() && validFile(filePath))) {
        throw new FileNotFoundException("The specified file in load not valid.");
      }
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while (true) {

          line = reader.readLine();
          if (line == null) {
            break;
          }
          line_number++;
          runCommand(line);
        }
      } catch (IOException e) {
        view.showError("Error in the file at line " + line_number + e);
      } catch (InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }

    } catch (FileNotFoundException e) {
      view.showError(String.valueOf(e));

    }
  }

  private boolean validFile(String filepath) {
    File directory = new File(filepath).getParentFile();
    return (directory != null && directory.exists() && directory.isDirectory());
  }


  protected void runCommand(String command)
      throws IOException, InstantiationException, IllegalAccessException {
    String[] tokens = command.split(" ");
    String params;
    String[] args;
    for (String element : tokens) {
      if (element.equals("split")) {
        boolean flag = false;
        ImageCommandType commandType = ImageCommandType.fromString(tokens[0]);
        ImageCommandType[] allowed_split = {ImageCommandType.BLUR, ImageCommandType.SHARPEN,
            ImageCommandType.SEPIA, ImageCommandType.VALUE_COMPONENT,
            ImageCommandType.COLOR_CORRECT, ImageCommandType.LEVEL_ADJUST};
        for (ImageCommandType e : allowed_split) {
          if (e == commandType) {
            flag = true;
            break;
          }
        }
        if (!flag) {
          throw new IllegalArgumentException("Unknown command: " + command);
        }
        args = (Arrays.copyOfRange(tokens, tokens.length - 4, tokens.length));
        params = String.join(" ", args);
        new Chop().runCommand(params, model);

        tokens[tokens.length - 4] = tokens[tokens.length - 3];
        String new_command = String.join(" ", Arrays.copyOfRange(tokens, 0, tokens.length - 2));
        runCommand(new_command);
        tokens = command.split(" ");
        String combine_command = tokens[tokens.length - 3] + " " + tokens[tokens.length - 4];
        new Underlay().runCommand(combine_command, model);
        return;
      }
    }

    String commandStr = tokens[0].toUpperCase(); // Convert to uppercase
    args = Arrays.copyOfRange(tokens, 1, tokens.length);
    params = String.join(" ", args);
    ImageCommandType commandType;
    try {
      commandType = ImageCommandType.fromString(commandStr);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown command: " + commandStr);
    }
    try {
      commandMap.get(commandType).runCommand(params, model);
    } catch (Exception e) {
      view.showError("Unknown Command");
    }
  }

  private Map<ImageCommandType, Command> getCommands() {
    Map<ImageCommandType, Command> commandMap = new HashMap<>();
    commandMap.put(ImageCommandType.LOAD,
        (params, model) -> new Load().runCommand(params, model));
    commandMap.put(ImageCommandType.SAVE,
        (params, model) -> new Save().runCommand(params, model));
    commandMap.put(ImageCommandType.BRIGHTEN,
        (params, model) -> new Brighten().runCommand(params, model));
    commandMap.put(ImageCommandType.VERTICAL_FLIP,
        (params, model) -> new VerticalFlip().runCommand(params, model));
    commandMap.put(ImageCommandType.HORIZONTAL_FLIP,
        (params, model) -> new HorizontalFlip().runCommand(params, model));
    commandMap.put(ImageCommandType.VALUE_COMPONENT,
        (params, model) -> new ValueComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.RGB_SPLIT,
        (params, model) -> new RGBSplit().runCommand(params, model));
    commandMap.put(ImageCommandType.RGB_COMBINE,
        (params, model) -> new RGBCombine().runCommand(params, model));
    commandMap.put(ImageCommandType.SEPIA,
        (params, model) -> new Sepia().runCommand(params, model));
    commandMap.put(ImageCommandType.BLUR,
        (params, model) -> new Blur().runCommand(params, model));
    commandMap.put(ImageCommandType.SHARPEN,
        (params, model) -> new Sharpen().runCommand(params, model));
    commandMap.put(ImageCommandType.INTENSITY_COMPONENT,
        (params, model) -> new IntensityComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.LUMA_COMPONENT,
        (params, model) -> new LumaComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.RED_COMPONENT,
        (params, model) -> new RedComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.GREEN_COMPONENT,
        (params, model) -> new GreenComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.BLUE_COMPONENT,
        (params, model) -> new BlueComponent().runCommand(params, model));
    commandMap.put(ImageCommandType.HISTOGRAM,
        (params, model) -> new Histogram().runCommand(params, model));
    commandMap.put(ImageCommandType.COLOR_CORRECT,
        (params, model) -> new ColorCorrect().runCommand(params, model));
    commandMap.put(ImageCommandType.LEVEL_ADJUST,
        (params, model) -> new LevelsAdjust().runCommand(params, model));
    commandMap.put(ImageCommandType.COMPRESS,
        (params, model) -> new Compress().runCommand(params, model));
    commandMap.put(ImageCommandType.CHOP,
        (params, model) -> new Chop().runCommand(params, model));
    commandMap.put(ImageCommandType.UNDERLAY,
        (params, model) -> new Underlay().runCommand(params, model));

    return commandMap;

  }

}