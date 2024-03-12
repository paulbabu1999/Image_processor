package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import model.IROModel;
import model.Model;
import model.ROModel;
import view.GUIView;
import view.IMEView;

/**
 * The Main class contains the main method for running the image processing controller.
 */
public class Main {

  /**
   * The Main method for running the image processing controller.
   *
   * @param args Command-line arguments (not used in this implementation).
   * @throws IOException if an I/O error occurs during the execution.
   */
  public static void main(String[] args) throws IOException {
    //IMEView view = new IMEView(System.out);
    Model model = new Model();
    IROModel rom = new ROModel(model);
    GUIView guiView = new GUIView(rom);
    //IMEController controller = new IMEController(new InputStreamReader(System.in),
    //    view, model);
    //controller.start(args);
    GUIController guiController = new GUIController(guiView,model);

    guiController.setView();

  }

}
