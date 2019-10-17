package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Abstract Pyramid Solitaire class. Contains methods and function of basic solitaire class+some
 * abstracted methods.
 * 
 * @author Akira Kato
 *
 */
public class AbstractPyramidSolitaireModel implements PyramidSolitaireModel<Card> {

  protected List<ArrayList<Card>> pyramid = new ArrayList<ArrayList<Card>>();
  protected List<Card> draw = new ArrayList<Card>();
  protected List<Card> stock;
  protected int numRows = -1;
  protected int numDraw = -1;
  protected boolean gameStarted = false;

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    ArrayList<String> suites = new ArrayList<String>(Arrays.asList("♣", "♥", "♠", "♦"));
    for (String suite : suites) {
      for (int j = 1; j < 14; j++) {
        deck.add(new Card(j, suite));
      }
    }
    return deck;
  }

  protected boolean validateDeck(List<Card> deck) {
    List<Card> validDeck = this.getDeck();
    List<Card> inputDeck = new ArrayList<>(deck);
    Collections.sort(validDeck);
    Collections.sort(inputDeck);
    if (validDeck.size() != inputDeck.size()) {
      return false;
    }
    for (int i = 0; i < inputDeck.size(); i++) {
      if (!validDeck.get(i).equals(inputDeck.get(i))) {
        return false;
      }
    }
    return true;
  }

  protected List<Card> dealPyramid(List<Card> deck) {
    List<Card> copyDeck = new ArrayList<>(deck);
    for (int row = 0; row < this.numRows; row++) {
      List<Card> tempRow = new ArrayList<Card>();
      for (int i = 0; i < row + 1; i++) {
        tempRow.add(copyDeck.get(0));
        copyDeck.remove(0);
      }
      this.pyramid.add((ArrayList<Card>) tempRow);
    }
    return copyDeck;
  }

  protected boolean checkMaxRow(int row) {
    return row > 9;
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {

    if (deck == null || numRows < 1 || numDraw < 0 || this.checkMaxRow(numRows)) {
      throw new IllegalArgumentException("Cant have negative numrows or numdraw");
    }

    if (!this.validateDeck(deck)) {
      throw new IllegalArgumentException("Deck invalid");
    }

    this.numDraw = numDraw;
    this.numRows = numRows;
    this.pyramid = new ArrayList<ArrayList<Card>>();
    this.draw = new ArrayList<Card>();
    this.stock = new ArrayList<Card>();
    List<Card> copyDeck = new ArrayList<>(deck);

    if (shouldShuffle) {
      Collections.shuffle(copyDeck);
    }

    copyDeck = this.dealPyramid(copyDeck);
    for (int i = 0; i < numDraw; i++) {
      if (copyDeck.size() == 0) {
        break;
      }
      this.draw.add(copyDeck.get(0));
      copyDeck.remove(0);
    }
    this.stock = copyDeck;
    this.gameStarted = true;
  }

  /**
   * Check if game started yet.
   * 
   * @throws IllegalStateException if game didn't start.
   */
  protected void gameStateException() {
    if (!this.gameStarted) {
      throw new IllegalStateException("game didnt start yet");
    }
  }

  /**
   * Check if card at given coordinate is exposed.
   * 
   * @param row row of the desired card.
   * @param card column of the desired card.
   * @throws IllegalArgumentException if row card is invalid.
   */
  protected boolean exposed(int row, int card) {
    if (this.checkRowCard(row, card)) {
      throw new IllegalArgumentException("invalid row/card value");
    }
    if (this.getCardAtReference(row, card) == null) {
      return false;
    }
    if (row == this.pyramid.size() - 1) {
      return true;
    } else {
      // System.out.println("left exposed: " + this.getCardAtReference(row + 1, card));
      // System.out.println("right exposed: " + this.getCardAtReference(row + 1, card + 1));
      return this.getCardAtReference(row + 1, card) == null
          && this.getCardAtReference(row + 1, card + 1) == null;
    }
  }

  /**
   * Check if given coordinate is valid or if it's empty.
   * 
   * @param row row of the desired card.
   * @param card column of the desired card.
   * @return true if given coordinate is invalid or empty, false otherwise.
   */
  protected boolean checkRowCard(int row, int card) {
    // System.out.println("row: " + row);
    // System.out.println("card: " + card);
    // System.out.println("this.pyramid.size() - 1: " + (this.pyramid.size() - 1));
    // System.out.println("this.getRowWidth(row) - 1: " + (this.getRowWidth(row) - 1));
    // System.out.println("this.getCardAtReference(row, card)" + this.getCardAtReference(row,
    // card));
    // System.out.println("this.getCardAtReference(row, card) == null: " +
    // (this.getCardAtReference(row, card) ==
    // null));

    return row < 0 || row > this.pyramid.size() - 1 || card < 0 || card > this.getRowWidth(row) - 1
        || this.getCardAtReference(row, card) == null;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    this.gameStateException();

    // System.out.println("Pyramid Before: " + this.pyramid);

    if (row1 == row2 && card1 == card2) {
      throw new IllegalArgumentException("same card!");
    }

    if (this.checkRowCard(row1, card1) || this.checkRowCard(row2, card2)) {
      throw new IllegalArgumentException("Invalid row/card value");
    }

    if (!(this.exposed(row1, card1) && this.exposed(row2, card2))) {
      throw new IllegalArgumentException("given cards are not exposed in pyramid");
    }

    Card c1 = this.getCardAtReference(row1, card1);
    Card c2 = this.getCardAtReference(row2, card2);
    // System.out.println("Card1: " + c1);
    // System.out.println("Card2: " + c2);

    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("The selected two cards do not sum to 13" + "card1= "
          + c1.getValue() + " card2= " + c2.getValue());
    }

    this.removeCardHelper(row1, card1);
    this.removeCardHelper(row2, card2);

    // System.out.println("Pyramid After: " + this.pyramid);
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {

    this.gameStateException();

    if (this.getCardAtReference(row, card).getValue() == 13) {
      this.removeCardHelper(row, card);
    } else {
      throw new IllegalArgumentException("selected card not King");
    }

  }

  protected void removeCardHelper(int row, int card) {
    this.gameStateException();

    // System.out.println("Pyramid Before: " + this.pyramid);

    if (this.checkRowCard(row, card)) {
      throw new IllegalArgumentException(
          "Cannot remove card invalid row or card value" + " or doesn't exist on pyramid");
    }

    if (!this.exposed(row, card)) {
      throw new IllegalArgumentException("given card is not exposed in pyramid");
    }
    // System.out.println("Before removing: " + this.pyramid.get(row));
    // System.out.println("Row: " + row + " Card: " + card);
    // System.out.println("The card to be removed: " + this.getCardAtReference(row, card));
    this.pyramid.get(row).set(card, Card.EMPTY_CARD);
    // System.out.println("After removing: " + this.pyramid.get(row));

    /*
     * boolean totallyEmpty = true; for (Card c : this.pyramid.get(row)) { totallyEmpty =
     * totallyEmpty && (c.getValue() == 0); }
     * 
     * if (totallyEmpty) { this.pyramid.remove(row); }
     */

    // System.out.println("Pyramid After: " + this.pyramid);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {

    this.gameStateException();

    if (drawIndex > this.draw.size() - 1 || drawIndex < 0 || this.checkRowCard(row, card)) {
      throw new IllegalArgumentException(
          "row/card value invalid or drawIndex invalid" + "row: " + row + "card: " + card
              + "index: " + drawIndex + "drawsize: " + this.getDrawCards().size());
    }
    if (!this.exposed(row, card)) {
      throw new IllegalArgumentException("given card is not exposed in pyramid");
    }

    Card c1 = this.draw.get(drawIndex);
    Card c2 = this.getCardAtReference(row, card);

    if (c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException(
          "the two cards do not sum to 13" + "card1= " + c1 + " card2= " + c2);
    }

    this.removeCardHelper(row, card);
    this.discardDraw(drawIndex);

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {

    this.gameStateException();

    if (drawIndex > this.draw.size() - 1 || drawIndex < 0) {
      throw new IllegalArgumentException("drawIndex invalid");
    }
    if (this.stock.size() != 0) {
      this.draw.set(drawIndex, this.stock.get(0));
      this.stock.remove(0);
    } else {
      this.draw.remove(drawIndex);
    }

  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public int getNumDraw() {
    return this.numDraw;
  }

  @Override
  public int getRowWidth(int row) {
    this.gameStateException();
    if (row < 0 || row > this.pyramid.size() - 1) {
      throw new IllegalArgumentException("invalid row");
    }
    return this.pyramid.get(row).size();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    this.gameStateException();
    if (this.getScore() == 0) {
      return true;
    }
    // System.out.println("score is not zero");
    if ((this.stock.size() + this.draw.size()) != 0) {
      // System.out.println("stock+draw is not zero");
      return false;
    } else {
      ArrayList<Card> exposedCards = new ArrayList<Card>();
      for (int i = 0; i < this.pyramid.size(); i++) {
        for (int j = 0; j < this.pyramid.get(i).size(); j++) {
          try {
            if (this.exposed(i, j)) {
              exposedCards.add(this.getCardAtReference(i, j));
            }
          } catch (IllegalArgumentException e) {
            // Do nothing as an exception thrown is an illegal card
          }
        }
      }
      // System.out.println("Exposed cards: " + exposedCards);

      for (Card c1 : exposedCards) {
        if (c1.getValue() == 13) {
          // System.out.println("is 13");
          return false;
        }
        for (Card c2 : exposedCards) {
          if (c1.getValue() + c2.getValue() == 13) {
            // System.out.println("add up to 13");
            return false;
          }
        }
      }

      // System.out.println("no exposed cards match so loss");

      return true;
    }
  }

  @Override
  public int getScore() throws IllegalStateException {
    this.gameStateException();
    int totalscore = 0;
    for (ArrayList<Card> alc : this.pyramid) {
      for (Card c : alc) {
        totalscore = totalscore + c.getValue();
      }
    }

    return totalscore;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    Card copy = this.getCardAtReference(row, card);
    if (copy == null) {
      return null;
    }
    copy = new Card(copy.getValue(), copy.getSuite());
    return copy;
  }

  protected Card getCardAtReference(int row, int card) throws IllegalStateException {
    this.gameStateException();
    if (row < 0 || row > this.numRows - 1 || card < 0 || card > this.getRowWidth(row) - 1) {
      throw new IllegalArgumentException("Cannot remove card invalid row or card value");
    }
    Card output = this.pyramid.get(row).get(card);
    if (output.getValue() == 0) {
      return null;
    } else {
      return output;
    }
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    this.gameStateException();
    List<Card> copy = new ArrayList<Card>(this.draw);
    return copy;
  }
}
