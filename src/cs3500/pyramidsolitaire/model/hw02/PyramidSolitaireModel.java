package cs3500.pyramidsolitaire.model.hw02;

import java.util.List;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 * 
 * @param <K> the type of cards this model uses
 */
public interface PyramidSolitaireModel<K> {
  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the game.
   *
   * @return the deck of cards as a list
   */
  List<K> getDeck();

  /**
   * <p>
   * Deal a new game of Pyramid Solitaire. The cards to be used and their order are specified by the
   * the given deck, unless the {@code shuffle} parameter indicates the order should be ignored.
   * </p>
   *
   * <p>
   * This method first verifies that the deck is valid. It deals cards into the characteristic
   * pyramid shape having the specified number of complete rows, followed by the specified number of
   * draw cards. The 0th card in {@code deck} is the top of the pyramid.
   * </p>
   *
   * <p>
   * This method should have no other side effects, and should work for any valid arguments.
   * </p>
   *
   * @param deck the deck to be dealt
   * @param shouldShuffle if {@code false}, use the order as given by {@code deck}, otherwise
   *        shuffle the cards
   * @param numRows number of rows in the pyramid
   * @param numDraw number of open piles
   * @throws IllegalArgumentException if the deck is null or invalid, or another input is invalid
   */
  void startGame(List<K> deck, boolean shouldShuffle, int numRows, int numDraw);

  /**
   * Execute a pairwise move on the pyramid, using the two specified card positions.
   * 
   * @param row1 row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2 row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void remove(int row1, int card1, int row2, int card2) throws IllegalStateException;

  /**
   * Execute a single-card move on the pyramid, using the specified card position.
   * 
   * @param row row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void remove(int row, int card) throws IllegalStateException;

  /**
   * Execute a pairwise move, using the specified card from the draw pile and the specified card
   * from the pyramid.
   * 
   * @param row row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the move is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException;

  /**
   * Discards an individual card from the draw pile.
   * 
   * @param drawIndex the card to be discarded
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException if the game has not yet been started
   */
  void discardDraw(int drawIndex) throws IllegalStateException;

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   * 
   * @return the height of the pyramid, or -1
   */
  int getNumRows();

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  int getNumDraw();

  /**
   * Returns the number of cards dealt into the given row.
   * 
   * @param row the desired row (zero-indexed)
   * @return the width of that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException if the game has not yet been started
   */
  int getRowWidth(int row);

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Return the current score, which is the sum of the values of the cards remaining in the pyramid.
   * 
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */
  int getScore() throws IllegalStateException;

  /**
   * Returns the card at the specified coordinates.
   * 
   * @param row row of the desired card
   * @param card column of the desired card
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException if the game hasn't been started yet
   */
  K getCardAt(int row, int card) throws IllegalStateException;

  /**
   * Returns the currently available draw cards. There should be at most
   * {@link PyramidSolitaireModel#getNumDraw} cards (the number specified when the game started) --
   * there may be fewer, if cards have been removed.
   * 
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  List<K> getDrawCards() throws IllegalStateException;
}
