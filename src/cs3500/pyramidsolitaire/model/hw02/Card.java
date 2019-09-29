package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a playing card. if value==0, it is considered an empty card. a static emptyCard is
 * provided.
 */
public class Card {
  private final int value;
  private final String suite;
  public static final Card EMPTY_CARD = new Card(0, "♣");

  /**
   * Constructor for playing card.
   * 
   * @param v value of playing card.
   * @param s the suite of playing card. Must be one of the 4 symbols.
   * @throws IllegalArgumentException if value or suite illegal.
   */
  public Card(int v, String s) {
    if (v < 0 || v > 13 || !(s.equals("♣") || s.equals("♥") || s.equals("♠") || s.equals("♦"))) {
      throw new IllegalArgumentException("value not in range (empty is 0) or suite doesn't exist");
    }
    this.value = v;
    this.suite = s;
  }

  @Override
  public String toString() {
    String output = "";
    if (value == 0) {
      output = "  ";
    } else if (value == 1) {
      output = "A" + suite;
    } else if (value <= 10) {
      output = value + suite;
    } else if (value == 11) {
      output = "J" + suite;
    } else if (value == 12) {
      output = "Q" + suite;
    } else if (value == 13) {
      output = "K" + suite;
    }
    /*
     * if (value != 10) { output = output + " "; }
     */
    // System.out.println("card tostring: "+output+".");
    return output;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }

    return (((Card) o).value == this.value && ((Card) o).suite == this.suite)
        || (((Card) o).value == 0 && this.value == 0);
  }

  @Override
  public int hashCode() {
    if (value == 0) {
      return 0;
    }
    if (suite.equals("♣")) {
      return value;
    } else if (suite.equals("♥")) {
      return value + 13;
    } else if (suite.equals("♠")) {
      return value + 26;
    } else {
      return value + 39;
    }
  }

  /**
   * gets the value.
   * 
   * @return this value
   */
  public int getValue() {
    return this.value;
  }

  /**
   * gets the suite.
   * 
   * @return this suite
   */
  public String getSuite() {
    return this.suite;
  }

}
