package controller;

import java.io.IOException;

/**
 * The `Controller` interface defines the contract for classes that control the flow and behavior of
 * a program or system. Classes implementing this interface are responsible for initiating and
 * managing the execution of specific tasks or processes.
 */
public interface Controller {

  /**
   * Starts the execution of the program or process controlled by the implementing class.
   *
   * @throws IOException if an I/O error occurs during the execution.
   */
  void start(String[] args) throws IOException;

}
