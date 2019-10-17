import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;

import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;

import cs3500.pyramidsolitaire.model.hw04.TriPeaks;

import static org.junit.Assert.assertEquals;

/**
 * Testing the creator for pyramid solitaire.
 * 
 * @author Akira Kato
 *
 */
public class Hw04TCreator {
  @Test
  public void testCreateBasic() {
    assertEquals(new BasicPyramidSolitaire(), PyramidSolitaireCreator.create(GameType.BASIC));
  }

  @Test
  public void testCreateRelaxed() {
    assertEquals(new RelaxedPyramidSolitaire(), PyramidSolitaireCreator.create(GameType.RELAXED));
  }

  @Test
  public void testCreateTri() {
    assertEquals(new TriPeaks(), PyramidSolitaireCreator.create(GameType.TRIPEAKS));
  }

}
