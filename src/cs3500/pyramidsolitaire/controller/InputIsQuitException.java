package cs3500.pyramidsolitaire.controller;

/**
 * represents an input is quit exception.
 * 
 * @author Akira Kato
 *
 */
public class InputIsQuitException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for inputsisquitexception class.
   * 
   * @param message the output message of exception.
   */
  public InputIsQuitException(String message) {
    super(message);
  }
}
