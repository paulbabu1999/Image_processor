package view;

import java.io.IOException;

/**
 * The `IMEView` class is responsible for displaying information to the user and handling user
 * prompts and errors.
 */
public class IMEView implements View {

  private final Appendable out;

  /**
   * Constructs an `IMEView` with the specified output stream.
   *
   * @param out The output stream to which information is printed.
   */
  public IMEView(Appendable out) {
    this.out = out;
  }

  /**
   * Prints the next prompt to the user based on whether the program is in script mode or command
   * mode.
   *
   * @param script Indicates if the program is in script mode (true) or command mode (false).
   * @throws IOException if an I/O error occurs during output.
   */
  @Override
  public void printNextPrompt(boolean script) throws IOException {
    if (script) {
      printEnterScriptFilePath();
    } else {
      printCommandOrScript();
    }
  }

  private void printCommandOrScript() throws IOException {
    this.out.append("Do you want to enter a single command or upload a script file?"
        + " Enter the command  or enter 'script' for uploading a script."
        + " Type 'quit' to stop.\n");
  }

  private void printEnterScriptFilePath() throws IOException {
    this.out.append("Enter the file path of the script file: \n");
  }

  public void showError(String message) throws IOException {
    this.out.append("Error: " + message + "\n");
  }


}