package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Represents basic pyramid solitaire.
 * 
 * @author Akira Kato
 *
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  private List<ArrayList<Card>> pyramid;
  private List<Card> draw;
  private List<Card> stock;
  private int numRows;
  private int numDraw;
  private boolean gameStarted;

  /**
   * Constructor for this class.
   */
  public BasicPyramidSolitaire() {
    this.pyramid = new ArrayList<ArrayList<Card>>();
    this.draw = new ArrayList<Card>();
    this.numRows = -1;
    this.numDraw = -1;
    this.gameStarted = false;
  }

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

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {
    if (deck == null || numRows < 1 || numDraw < 0 || numRows > 9) {
      throw new IllegalArgumentException("Cant have negative numrows or numdraw");
    }

    // System.out.println("Valid deck: " + this.deck);

    HashSet<Card> cards = new HashSet<>(deck);

    if (!(deck.size() == cards.size() && cards.size() == 52)) {
      throw new IllegalArgumentException("Deck invalid");
    }

    this.pyramid = new ArrayList<ArrayList<Card>>();
    this.draw = new ArrayList<Card>();
    List<Card> copyDeck = new ArrayList<>(deck);
    if (shouldShuffle) {
      Collections.shuffle(copyDeck);
    }

    for (int row = 0; row < numRows; row++) {
      List<Card> tempRow = new ArrayList<Card>();
      for (int i = 0; i < row + 1; i++) {
        tempRow.add(copyDeck.get(0));
        copyDeck.remove(0);
      }
      this.pyramid.add((ArrayList<Card>) tempRow);
    }

    // System.out.println("Pyramid: " + this.pyramid);

    for (int i = 0; i < numDraw; i++) {
      if (copyDeck.size() == 0) {
        break;
      }
      this.draw.add(copyDeck.get(0));
      copyDeck.remove(0);
    }

    // System.out.println("Draw: " + this.draw);

    this.stock = copyDeck;

    // System.out.println("Stock: " + this.stock);

    this.numDraw = numDraw;
    this.numRows = numRows;
    this.gameStarted = true;
  }

  /**
   * Check if game started yet.
   * 
   * @throws IllegalStateException if game didn't start.
   */
  private void gameStateException() {
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
  private boolean exposed(int row, int card) {
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
  private boolean checkRowCard(int row, int card) {
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

  private void removeCardHelper(int row, int card) {
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
    return row + 1;
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

  private Card getCardAtReference(int row, int card) throws IllegalStateException {
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
