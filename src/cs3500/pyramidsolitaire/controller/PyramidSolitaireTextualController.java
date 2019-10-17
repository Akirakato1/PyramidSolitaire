package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

/**
 * Represents textual controller for pyramid solitaire.
 * 
 * @author Akira Kato
 *
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private Readable read;
  private Appendable out;

  /**
   * Constructor for textual controller.
   * 
   * @param rd Readable input.
   * @param ap Appendable input.
   * @throws IllegalArgumentException when readable or appendable are null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("readable or appendable null");
    }

    this.read = rd;
    this.out = ap;

  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("model or deck are null \n");
    }
    int[] inputs = new int[4];
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("startGame threw illegalargumentexception");
    }
    Scanner scan = new Scanner(this.read);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model, this.out);
    while (!model.isGameOver()) {

      try {
        view.render();
        this.out.append("\nScore: " + model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("IO exception");
      }
      String command = "";
      if (scan.hasNext()) {
        command = scan.next();
      } else {
        break;
      }
      try {
        if (command.equals("rm1")) {
          inputs[0] = this.getNextInput(scan);
          inputs[1] = this.getNextInput(scan);
          try {
            model.remove(inputs[0], inputs[1]);
          } catch (IllegalArgumentException e) {
            this.appendWithExceptionMessage(
                "Invalid move. Play again. " + "rm1 had illegal argument");
          }
        } else if (command.equals("rm2")) {
          inputs[0] = this.getNextInput(scan);
          inputs[1] = this.getNextInput(scan);
          inputs[2] = this.getNextInput(scan);
          inputs[3] = this.getNextInput(scan);
          try {
            model.remove(inputs[0], inputs[1], inputs[2], inputs[3]);
          } catch (IllegalArgumentException e) {
            this.appendWithExceptionMessage(
                "Invalid move. Play again. " + "rm2 had illegal argument");
          }
        } else if (command.equals("rmwd")) {
          inputs[0] = this.getNextInput(scan);
          inputs[1] = this.getNextInput(scan);
          inputs[2] = this.getNextInput(scan);
          try {
            model.removeUsingDraw(inputs[0], inputs[1], inputs[2]);
          } catch (IllegalArgumentException e) {
            this.appendWithExceptionMessage(
                "Invalid move. Play again. " + "rmwd had illegal argument");
          }
        } else if (command.equals("dd")) {
          inputs[0] = this.getNextInput(scan);
          try {
            model.discardDraw(inputs[0]);
          } catch (IllegalArgumentException e) {
            this.appendWithExceptionMessage(
                "Invalid move. Play again. " + "dd had illegal argument");
          }
        } else if (command.equals("q")) {
          break;
        } else {
          this.appendWithExceptionMessage("Invalid move. Play again.");
        }
      } catch (InputIsQuitException e) {
        break;
      }
    }
    if (model.isGameOver()) {
      try {
        view.render();
      } catch (IOException e) {
        throw new IllegalStateException("Appendable failed to take");
      }
    } else {
      try {
        this.out.append("Game quit!\nState of game when quit:\n");
        view.render();
        this.out.append("\nScore: " + model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Appendable failed to take");
      }
    }

    scan.close();
  }

  private int getNextInput(Scanner scan) throws InputIsQuitException {
    String input = scan.next();
    int num;
    while (true) {
      if (input.equalsIgnoreCase("q")) {
        throw new InputIsQuitException("Input is quit");
      }
      try {
        num = Integer.parseInt(input);
        return num - 1;
      } catch (NumberFormatException e) {
        this.appendWithExceptionMessage("Bad Argument: " + input);
      }
      input = scan.next();
    }
  }

  void appendWithExceptionMessage(String message) {
    try {
      this.out.append(message + "\n");
    } catch (IOException e1) {
      throw new IllegalStateException("Appendable failed to take\n");
    }
  }

}
