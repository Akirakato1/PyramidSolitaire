package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Pyramid Solitaire Creator that contains a static method to create model of given type.
 * 
 * @author Akira Kato
 *
 */
public class PyramidSolitaireCreator {

  /**
   * To create model of given gametype.
   * 
   * @param gametype enum BASIC, TRIPEAKS, or RELAXED.
   * @return PyramidSolitaireModel (model).
   */
  public static PyramidSolitaireModel create(GameType gametype) {
    switch (gametype) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case TRIPEAKS:
        return new TriPeaks();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      default:
        return null;
    }
  }


  /**
   * Represets the gametype available for Pyramid Solitaire.
   * 
   * @author Akira Kato
   *
   */
  public enum GameType {
    BASIC, TRIPEAKS, RELAXED;
  }

}
