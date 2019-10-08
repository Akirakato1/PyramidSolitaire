package cs3500.pyramidsolitaire.controller;

import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents PyramidSolitaireController interface. 
 * @author Akira Kato
 *
 */
public interface PyramidSolitaireController {

  /**
   * Plays the game of given model.
   * 
   * @param model
   * @param deck
   * @param shuffle
   * @param numRows
   * @param numDraw
   * @throws IllegalArgumentException if model is null
   * @throws IllegalStateException if unable to successfully receive input or transmit output, or if
   *         the game cannot be started.
   */
  <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle, int numRows,
      int numDraw);

}
