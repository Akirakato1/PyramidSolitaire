package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Represents a TriPeak game. Have 3 overlapping pyramids instead of one.
 * 
 * @author Akira Kato
 *
 */
public class TriPeaks extends AbstractPyramidSolitaireModel {
  @Override
  protected List<Card> dealPyramid(List<Card> deck) {
    List<Card> copyDeck = new ArrayList<>(deck);
    int gaps = Utils.truncNat((int) (super.numRows * 1.0 / 2));
    int initGaps = super.numRows;
    if (initGaps % 2 == 0) {
      initGaps = super.numRows + 1;
    }
    if (super.numRows == 1) {
      initGaps = 3;
    }
    for (int row = 0; row < super.numRows; row++) {
      List<Card> tempRow = new ArrayList<Card>();
      for (int col = 0; col < row + initGaps; col++) {
        if (col % (gaps + row) < row + 1) {
          tempRow.add(copyDeck.get(0));
          copyDeck.remove(0);
        } else {
          tempRow.add(Card.EMPTY_CARD);
        }
      }
      gaps = Utils.truncNat(gaps - 1);
      this.pyramid.add((ArrayList<Card>) tempRow);
    }
    System.out.println(this.pyramid);
    return copyDeck;
  }

  @Override
  protected boolean checkMaxRow(int row) {
    return row > 8;
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    ArrayList<String> suites = new ArrayList<String>(Arrays.asList("♣", "♥", "♠", "♦"));
    for (String suite : suites) {
      for (int j = 1; j < 14; j++) {
        deck.add(new Card(j, suite));
        deck.add(new Card(j, suite));
      }
    }
    return deck;
  }

}
