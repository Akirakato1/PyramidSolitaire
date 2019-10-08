import org.junit.Test;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

import cs3500.pyramidsolitaire.model.hw02.Card;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import java.io.Reader;

import java.io.StringReader;

/**
 * For testing hw3.
 * 
 * @author Akira Kato
 *
 */
public class Hw03Tests {
  PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelPlayGame() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    controller.playGame(null, model.getDeck(), true, 5, 5);
  }

  @Test
  public void testBadAppendableController() {
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(in, new BadAppendable());
    controller.playGame(this.model, this.model.getDeck(), true, 5, 5);
  }

  @Test
  public void testRednderIOException() {
    PyramidSolitaireTextualView view =
        new PyramidSolitaireTextualView(this.model, new BadAppendable());
    String result = "";
    try {
      view.render();
    } catch (IOException e) {
      result = "IOException";
    }
    assertEquals(result, "IOException");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPyramidControllerNullInput() {
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(null, null);
    // just to eliminate yellow underline
    controller.playGame(null, null, true, 1, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGame1() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    controller.playGame(this.model, null, true, 5, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGame2() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    controller.playGame(this.model, this.model.getDeck(), true, -1, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGame3() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    controller.playGame(this.model, this.model.getDeck(), true, 5, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGame4() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    controller.playGame(this.model, this.model.getDeck(), true, 200, 3);
  }

  @Test
  public void testPlayGameRm1Bad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 4");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    System.out.println(out);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n"
            + "Invalid move. Play again. rm1 had illegal argument\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
            + "Draw: 3♥\r\n" + "Score: 94\n");
  }

  @Test
  public void testPlayGameRm1() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm1 5 3");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣      A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 81\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣      A♥  2♥\r\n"
            + "Draw: 3♥\r\n" + "Score: 81\n");
  }

  @Test
  public void testPlayGameRm2() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 5 2 5 4");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣      K♣      2♥\r\n" + "Draw: 3♥\r\n" + "Score: 81\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣      K♣      2♥\r\n"
            + "Draw: 3♥\r\n" + "Score: 81\n");
  }

  @Test
  public void testPlayGameRm2Bad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rm2 5 2 5 5");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n"
            + "Invalid move. Play again. rm2 had illegal argument\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
            + "Draw: 3♥\r\n" + "Score: 94\n");
  }

  @Test
  public void testPlayGameRmwd() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 9 5 5");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 13);
    assertEquals(out, "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n"
        + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
        + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥, A♠, 2♠\r\n" + "Score: 94\r\n"
        + "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
        + "J♣  Q♣  K♣  A♥\r\n" + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, 3♠, Q♥, K♥, A♠, 2♠\r\n"
        + "Score: 92\r\n" + "Game quit!\r\n" + "State of game when quit:\r\n" + "        A♣\r\n"
        + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥\r\n"
        + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, 3♠, Q♥, K♥, A♠, 2♠\r\n" + "Score: 92\n");
  }


  @Test
  public void testPlayGameRmwdBad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 9 5 2");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 13);
    assertEquals(out, "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n"
        + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
        + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥, A♠, 2♠\r\n" + "Score: 94\r\n"
        + "Invalid move. Play again. rmwd had illegal argument\r\n" + "        A♣\r\n"
        + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
        + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥, A♠, 2♠\r\n"
        + "Score: 94\r\n" + "Game quit!\r\n" + "State of game when quit:\r\n" + "        A♣\r\n"
        + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
        + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥, A♠, 2♠\r\n"
        + "Score: 94\n");
  }

  @Test
  public void testPlayGameDd() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 4♥\r\n" + "Score: 94\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
            + "Draw: 4♥\r\n" + "Score: 94\n");
  }

  @Test
  public void testPlayGameDdBad() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 2");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n"
            + "Invalid move. Play again. dd had illegal argument\r\n" + "        A♣\r\n"
            + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "Game quit!\r\n"
            + "State of game when quit:\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
            + "Draw: 3♥\r\n" + "Score: 94\r\n");
  }

  @Test
  public void testPlayGameBadArgumentsAndQuit() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd bad bad 1 bad q");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 5, 1);
    assertEquals(out,
        "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 3♥\r\n" + "Score: 94\r\n" + "Bad Argument: bad\r\n"
            + "Bad Argument: bad\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n"
            + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 4♥\r\n" + "Score: 94\r\n"
            + "Invalid move. Play again.\r\n" + "        A♣\r\n" + "      2♣  3♣\r\n"
            + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n" + "J♣  Q♣  K♣  A♥  2♥\r\n"
            + "Draw: 4♥\r\n" + "Score: 94\r\n" + "Game quit!\r\n" + "State of game when quit:\r\n"
            + "        A♣\r\n" + "      2♣  3♣\r\n" + "    4♣  5♣  6♣\r\n" + "  7♣  8♣  9♣  10♣\r\n"
            + "J♣  Q♣  K♣  A♥  2♥\r\n" + "Draw: 4♥\r\n" + "Score: 94\n");
  }

  @Test
  public void testPlayGameWin() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("rmwd 8 2 1 rmwd 7 2 2 rmwd 9 1 1");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 2, 13);
    assertEquals(out,
        "  A♣\r\n" + "2♣  3♣\r\n" + "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣, A♥, 2♥, 3♥\r\n"
            + "Score: 6\r\n" + "  A♣\r\n" + "    3♣\r\n"
            + "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, 4♥, Q♣, K♣, A♥, 2♥, 3♥\r\n" + "Score: 4\r\n"
            + "  A♣\r\n" + "\r\n" + "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 5♥, 4♥, Q♣, K♣, A♥, 2♥, 3♥\r\n"
            + "Score: 1\r\n" + "You win!");
  }

  @Test
  public void testPlayGameLose() {
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 "
        + "dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 "
        + "dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd "
        + "1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 dd 1 ");
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in, out);
    PyramidSolitaireModel<Card> game = new BasicPyramidSolitaire();
    controller.playGame(game, game.getDeck(), false, 4, 1);
    assertEquals(out, "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n"
        + "Draw: J♣\r\n" + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: Q♣\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: K♣\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: A♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 2♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 3♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 4♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 5♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 6♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 7♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 8♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 9♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 10♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: J♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: Q♥\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: K♥\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: A♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 2♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 3♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 4♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 5♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 6♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 7♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 8♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 9♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 10♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: J♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: Q♠\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: K♠\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: A♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 2♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 3♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 4♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 5♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 6♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 7♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 8♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: 9♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: 10♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: J♦\r\n" + "Score: 55\r\n" + "      A♣\r\n"
        + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n" + "7♣  8♣  9♣  10♣\r\n" + "Draw: Q♦\r\n"
        + "Score: 55\r\n" + "      A♣\r\n" + "    2♣  3♣\r\n" + "  4♣  5♣  6♣\r\n"
        + "7♣  8♣  9♣  10♣\r\n" + "Draw: K♦\r\n" + "Score: 55\r\n" + "Game over. Score: 55");
  }

}
