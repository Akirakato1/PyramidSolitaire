package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

/**
 * Main class for PyramidSolitaire games. Have Basic,Relax, and TriPeaks as gamemodes.
 * 
 * @author Akira Kato
 *
 */
public final class PyramidSolitaire {
  /**
   * The main function to run the game.
   * 
   * @param args user input to main function.
   */
  public static void main(String[] args) {
    PyramidSolitaireCreator creator = new PyramidSolitaireCreator();
    PyramidSolitaireModel<Card> model;
    PyramidSolitaireTextualController controller;
    int rows = 0;
    int draws = 0;
    try {
      if (args.length == 1) {
        rows = 7;
        draws = 3;
      } else if (args.length == 3) {
        rows = Integer.parseInt(args[1]);
        draws = Integer.parseInt(args[2]);
      }
      if (rows < 1 || draws < 0) {
        throw new IllegalArgumentException("bad value");
      }

      switch (args[0]) {
        case "basic":
          model = creator.create(GameType.BASIC);
          if (rows > 9) {
            throw new IllegalArgumentException("bad value");
          }
          break;
        case "tripeaks":
          model = creator.create(GameType.TRIPEAKS);
          if (rows > 8) {
            throw new IllegalArgumentException("bad value");
          }
          break;
        case "relaxed":
          model = creator.create(GameType.RELAXED);
          if (rows > 9) {
            throw new IllegalArgumentException("bad value");
          }
          break;
        default:
          throw new IllegalArgumentException("no gametype");
      }
      controller =
          new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
      controller.playGame(model, model.getDeck(), false, rows, draws);

    } catch (NumberFormatException e) {
      // should do nothing if there is exception since invalid game starting condition.
    } catch (IllegalArgumentException e2) {
      // should do nothing if there is exception since invalid game starting condition.
    }

  }
}
