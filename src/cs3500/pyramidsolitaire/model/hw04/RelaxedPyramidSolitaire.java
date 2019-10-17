package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Represents Relaxed Pyramid Solitaire. Can allow pairs to be removed if one is partially covered
 * by other.
 * 
 * @author Akira Kato
 *
 */
public class RelaxedPyramidSolitaire extends AbstractPyramidSolitaireModel {
  @Override
  public boolean isGameOver() throws IllegalStateException {
    super.gameStateException();
    for (int i = 0; i < this.pyramid.size() - 1; i++) {
      for (int j = 0; j < this.pyramid.get(i).size(); j++) {
        try {
          if (this.halfExposed(i, j)) {
            if ((super.exposed(i + 1, j) && (super.getCardAtReference(i, j).getValue()
                + super.getCardAtReference(i + 1, j).getValue() == 13))
                || (super.exposed(i + 1, j + 1) && (super.getCardAtReference(i, j).getValue()
                    + super.getCardAtReference(i + 1, j + 1).getValue() == 13))) {
              return false;
            }
          }
        } catch (IllegalArgumentException e) {
          // Do nothing as an exception thrown is an illegal card
        }
      }
    }
    return super.isGameOver();
  }

  private boolean halfExposed(int row, int card) {
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
      return Utils.xor(this.getCardAtReference(row + 1, card) == null,
          this.getCardAtReference(row + 1, card + 1) == null);
    }
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    try {
      super.remove(row1, card1, row2, card2);
    } catch (IllegalArgumentException e) {
      if (row1 == row2 && card1 == card2) {
        throw new IllegalArgumentException("same card!");
      }

      if (this.checkRowCard(row1, card1) || this.checkRowCard(row2, card2)) {
        throw new IllegalArgumentException("Invalid row/card value");
      }

      if (!((row1 == row2 + 1) && (card1 == card2 || card1 == card2 + 1)
          || (row2 == row1 + 1) && (card1 == card2 || card2 == card1 + 1))) {
        throw new IllegalArgumentException("the two cards are not pairs");
      }

      if (!(this.halfExposed(row1, card1) && super.exposed(row2, card2)
          || this.halfExposed(row2, card2) && super.exposed(row1, card1))) {
        throw new IllegalArgumentException(
            "given cards are not exactly one exposed and one half exposed");
      }

      Card c1 = this.getCardAtReference(row1, card1);
      Card c2 = this.getCardAtReference(row2, card2);

      if (c1.getValue() + c2.getValue() != 13) {
        throw new IllegalArgumentException("The selected two cards do not sum to 13" + "card1= "
            + c1.getValue() + " card2= " + c2.getValue());
      }
      this.pyramid.get(row1).set(card1, Card.EMPTY_CARD);
      this.pyramid.get(row2).set(card2, Card.EMPTY_CARD);
    }

  }



}
