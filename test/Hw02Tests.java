import org.junit.Test;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

import cs3500.pyramidsolitaire.model.hw02.Card;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import java.util.List;


/**
 * Testing for homework 2.
 */
public class Hw02Tests {
  PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
  PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model);
  PyramidSolitaireModel<Card> wonModel = new BasicPyramidSolitaire();
  PyramidSolitaireTextualView wonView = new PyramidSolitaireTextualView(wonModel);
  PyramidSolitaireModel<Card> lossModel = new BasicPyramidSolitaire();
  PyramidSolitaireTextualView lossView = new PyramidSolitaireTextualView(lossModel);
  Card aceSpade = new Card(1, "♠");
  Card aceSpade2 = new Card(1, "♠");
  Card sevenClover = new Card(7, "♣");
  Card eightClover = new Card(8, "♣");
  List<Card> validDeck = model.getDeck();
  List<Card> bigDeck = model.getDeck();
  List<Card> smallDeck = model.getDeck();
  List<Card> dupeDeck = model.getDeck();
  List<Card> shiftedDeck = model.getDeck();
  List<Card> validDraw = new ArrayList<Card>();

  /**
   * initialise some test data.
   */
  public void initData() {
    bigDeck.add(aceSpade);
    smallDeck.remove(0);
    dupeDeck.remove(4);
    dupeDeck.add(aceSpade);
    validDraw.add(sevenClover);
    validDraw.add(eightClover);

    Card temp = shiftedDeck.get(0);
    shiftedDeck.set(0, shiftedDeck.get(51));
    shiftedDeck.set(51, temp);
    System.out.println(shiftedDeck);
    wonModel.startGame(shiftedDeck, false, 1, 51);
    wonModel.remove(0, 0);

    lossModel.startGame(lossModel.getDeck(), false, 1, 51);
    while (lossModel.getDrawCards().size() != 0) {
      lossModel.discardDraw(0);
    }

    System.out.println(lossModel.getDrawCards());
    System.out.println(lossModel.getScore());
    System.out.println(lossView.toString());

  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart1() {
    model.getCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart2() {
    model.remove(0, 0, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart3() {
    model.remove(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart4() {
    model.removeUsingDraw(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart5() {
    model.discardDraw(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart6() {
    model.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart7() {
    model.getDrawCards();
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart8() {
    model.getCardAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart9() {
    model.getScore();
  }

  @Test(expected = IllegalStateException.class)
  public void testGameStart10() {
    model.getRowWidth(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameFunction1() {
    model.startGame(model.getDeck(), true, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameFunction2() {
    model.startGame(model.getDeck(), true, 3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameFunction3() {
    List<Card> testdeck = model.getDeck();
    testdeck.remove(0);
    model.startGame(testdeck, true, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameFunction4() {
    List<Card> testdeck = model.getDeck();
    testdeck.remove(0);
    testdeck.add(testdeck.get(0));
    model.startGame(testdeck, true, 3, 1);
  }
  /*
   * @Test public void testView() { model.startGame(model.getDeck(), false, 5, 5);
   * System.out.println(view.toString()); }
   */

  @Test
  public void testStartGameFunction5() {
    this.initData();
    model.startGame(model.getDeck(), false, 3, 2);
    model.startGame(model.getDeck(), false, 3, 2);
    assertEquals(2, model.getNumDraw());
    assertEquals(3, model.getNumRows());
    assertEquals(this.validDraw, model.getDrawCards());
  }

  @Test
  public void testRemoveTwo1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(4, 0, 4, 4);
    assertEquals(null, model.getCardAt(4, 0));
    assertEquals(null, model.getCardAt(4, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo2() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(4, 0, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo3() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(-4, 0, 4, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo4() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(20, 0, 4, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTwo5() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(0, 0, 4, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemove1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(-4, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove2() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(15, 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove3() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(4, 1);
  }

  @Test
  public void testRemove4() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 2);
    model.remove(4, 2);
    assertEquals(null, model.getCardAt(4, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemove5() {
    this.initData();
    model.startGame(model.getDeck(), false, 6, 13);
    model.remove(4, 2);
  }

  @Test
  public void testRemoveDraw1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(12, 4, 0);
    assertEquals(model.getCardAt(4, 0), null);
    assertEquals(3, model.getDrawCards().get(12).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDraw2() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(12, 4, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDraw3() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(-12, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDraw4() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(12, -4, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDraw5() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(20, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDraw6() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 13);
    model.removeUsingDraw(9, 0, 0);
  }

  @Test
  public void testDiscard1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    model.discardDraw(0);
    assertEquals(6, model.getDrawCards().get(0).getValue());
  }

  @Test
  public void testDiscard2() {
    this.initData();
    model.startGame(model.getDeck(), false, 1, 51);
    model.discardDraw(0);
    assertEquals(50, model.getDrawCards().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscard3() {
    this.initData();
    model.startGame(model.getDeck(), false, 1, 51);
    model.discardDraw(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDiscard4() {
    this.initData();
    model.startGame(model.getDeck(), false, 1, 51);
    model.discardDraw(51);
  }

  @Test
  public void testNumDraw1() {
    this.initData();
    model.startGame(model.getDeck(), false, 1, 51);
    assertEquals(model.getNumDraw(), 51);
  }

  @Test
  public void testNumDraw2() {
    this.initData();
    assertEquals(model.getNumDraw(), -1);
  }

  @Test
  public void testNumRows1() {
    this.initData();
    model.startGame(model.getDeck(), false, 1, 51);
    assertEquals(model.getNumRows(), 1);
  }

  @Test
  public void testNumRows2() {
    this.initData();
    assertEquals(model.getNumRows(), -1);
  }

  @Test
  public void testGetRowWidth1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    assertEquals(model.getRowWidth(2), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidth2() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    model.getRowWidth(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetRowWidth3() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    model.getRowWidth(5);
  }

  @Test
  public void testGetScore1() {
    this.initData();
    model.startGame(model.getDeck(), false, 2, 3);
    assertEquals(model.getScore(), 6);
  }

  @Test
  public void testGetCardAt1() {
    this.initData();
    model.startGame(model.getDeck(), false, 2, 3);
    assertEquals(new Card(1, "♣"), model.getCardAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAt2() {
    this.initData();
    model.startGame(model.getDeck(), false, 2, 3);
    model.getCardAt(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAt3() {
    this.initData();
    model.startGame(model.getDeck(), false, 2, 3);
    model.getCardAt(4, 4);
  }

  @Test
  public void testGetDrawCards() {
    this.initData();
    model.startGame(model.getDeck(), false, 3, 2);
    assertEquals(this.validDraw, model.getDrawCards());
  }

  @Test
  public void testCardEquals1() {
    this.initData();
    assertEquals(true, this.aceSpade.equals(this.aceSpade2));
  }

  @Test
  public void testCardEquals2() {
    this.initData();
    assertEquals(false, this.aceSpade.equals(this.eightClover));
  }

  @Test
  public void testCardToString() {
    this.initData();
    assertEquals("A♠", this.aceSpade.toString());
    assertEquals("  ", Card.EMPTY_CARD.toString());
  }

  @Test
  public void testCardGetSuite() {
    this.initData();
    assertEquals("♠", this.aceSpade.getSuite());
  }

  @Test
  public void testCardGetValue() {
    this.initData();
    assertEquals(1, this.aceSpade.getValue());
  }

  @Test
  public void testHashCode() {
    this.initData();
    assertEquals(27, this.aceSpade.hashCode());
    assertEquals(0, Card.EMPTY_CARD.hashCode());
  }

  @Test
  public void testPyramidView1() {
    this.initData();
    assertEquals("", this.view.toString());
  }

  @Test
  public void testPyramidView2() {
    this.initData();
    assertEquals("You win!", this.wonView.toString());
  }

  @Test
  public void testPyramidView3() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    assertEquals("        A♣\n" + "      2♣  3♣\n" + "    4♣  5♣  6♣\n" + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  K♣  A♥  2♥\n" + "Draw: 3♥, 4♥, 5♥", this.view.toString());
  }
  
  @Test
  public void testIsGameOver1() {
    this.initData();
    model.startGame(model.getDeck(), false, 5, 3);
    assertEquals(false,model.isGameOver());
  }
  
  @Test
  public void testIsGameOver2() {
    this.initData();
    assertEquals(true,wonModel.isGameOver());
  }
  
  @Test
  public void testIsGameOver3() {
    this.initData();
    assertEquals(true,lossModel.isGameOver());
  }
  
  @Test
  public void testPyramidView4() {
    this.initData();
    assertEquals("Game over. Score: 1",lossView.toString());
  }


}
