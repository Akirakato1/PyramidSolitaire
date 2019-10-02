package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import javax.management.modelmbean.ModelMBean;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private Readable read;
  private Appendable out;

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
    Objects.requireNonNull(model, "model can't be null");

    Scanner scan = new Scanner(this.read);
    model.startGame(deck, shuffle, numRows, numDraw);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model, this.out);
    boolean quitState = true;
    while (!model.isGameOver() && quitState) {

      try {
        view.render();
        this.out.append("\nScore: " + model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("IO exception");
      }

      String command = scan.next();
      quitState = this.inputProcessing(command, scan, view, model);

    }
    try {
      this.out.append("Game quit!\nState of game when quit:\n");
      view.render();
      this.out.append("\nScore: " + model.getScore() + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable failed to take");
    }
  }

  private ArrayList<Integer> inputValidation(int arguments, Scanner scan,
      PyramidSolitaireTextualView view) {
    String input = "";
    int count = arguments;
    ArrayList<Integer> output = new ArrayList<Integer>();
    String line = scan.nextLine();
    Scanner scanLine = new Scanner(line);
    while (count != 0) {
      if (!scanLine.hasNext()) {
        try {
          this.out.append("Reenter argument from number: " + (arguments - count + 1) + "\n");
          line = scan.nextLine();
          scanLine = new Scanner(line);
        } catch (IOException e) {
          throw new IllegalStateException("Appendable failed to take");
        }
      }
      input = scanLine.next();
      if (line.contains(" q ") || line.contains(" Q ") || line.contains(" q") || line.contains(" Q")
          || line.contains("q") || line.contains("Q")) {
        return null;
      }
      try {
        output.add(Integer.parseInt(input) - 1);
        count--;
      } catch (NumberFormatException e) {
        // do nothing
      }

    }
    return output;
  }

  private <K> boolean inputProcessing(String command, Scanner scan,
      PyramidSolitaireTextualView view, PyramidSolitaireModel<K> model) {

    ArrayList<Integer> processed;
    System.out.println("command: " + command);
    if (command.equals("rm1")) {
      processed = this.inputValidation(2, scan, view);
      if (processed == null) {
        return false;
      }
      try {
        model.remove(processed.get(0), processed.get(1));
      } catch (IllegalArgumentException e) {
        try {
          this.out.append("Bad Arguments for rm1");
          this.inputProcessing("rm1", scan, view, model);
        } catch (IOException e1) {
          throw new IllegalStateException("IO exception");
        }
      }
    } else if (command.equals("rm2\n")) {
      processed = this.inputValidation(4, scan, view);
      if (processed == null) {
        return false;
      }
      try {
        model.remove(processed.get(0), processed.get(1), processed.get(2), processed.get(3));
      } catch (IllegalArgumentException e) {
        try {
          this.out.append("Bad Arguments for rm2\n");
          this.inputProcessing("rm2", scan, view, model);
        } catch (IOException e1) {
          throw new IllegalStateException("IO exception");
        }
      }
    } else if (command.equals("rmwd")) {
      processed = this.inputValidation(3, scan, view);
      if (processed == null) {
        return false;
      }
      try {
        model.removeUsingDraw(processed.get(0), processed.get(1), processed.get(2));
      } catch (IllegalArgumentException e) {
        try {
          this.out.append("Bad Arguments for remove with draw\n");
          this.inputProcessing("rmwd", scan, view, model);
        } catch (IOException e1) {
          throw new IllegalStateException("IO exception");
        }
      }
    } else if (command.equals("dd")) {
      processed = this.inputValidation(1, scan, view);
      if (processed == null) {
        return false;
      }
      try {
        model.discardDraw(processed.get(0));
      } catch (IllegalArgumentException e) {
        try {
          this.out.append("Bad Arguments for discard\n");
          this.inputProcessing("dd", scan, view, model);
        } catch (IOException e1) {
          throw new IllegalStateException("IO exception");
        }
      }
    } else if (command.equalsIgnoreCase("q")) {
      return false;
    } else {
      try {
        this.out.append("Invalid move\n");
      } catch (IOException e) {
        throw new IllegalStateException("Appendable failed to take");
      }
    }
    return true;
  }

}
