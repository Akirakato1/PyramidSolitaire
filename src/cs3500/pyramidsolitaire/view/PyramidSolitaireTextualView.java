package cs3500.pyramidsolitaire.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents the view of pyramid solitaire. Does not depent on type of card provided in model.
 *
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView{
  private final PyramidSolitaireModel<?> model;
  private final Appendable out;
  // ... any other fields you need

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.out=ap;
  }
  /**
   * Constructor for view.
   * 
   * @param model the model of solitaire.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.out = null;
  }

  @Override
  public String toString() {
    if (this.model.getNumRows() == -1) {
      return "";
    }
    if (this.model.getScore() == 0) {
      return "You win!";
    }
    if (this.model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    }
    ArrayList<String> pyramidView = new ArrayList<String>();
    for (int i = 0; i < model.getNumRows(); i++) {
      String currentRowToString = "";
      for (int j = 0; j < model.getRowWidth(i); j++) {
        Object currentCard = model.getCardAt(i, j);
        if (currentCard == null) {
          currentRowToString = currentRowToString + " " + "   ";
        } else {
          String currentCardString = currentCard.toString();
          if (currentCardString.length() == 2) {
            currentCardString = currentCardString + " ";
          }
          currentRowToString = currentRowToString + " " + currentCardString;
          // System.out.println("current row string: "+currentRowToString);
        }
      }
      String paddingSpaces = "";
      for (int k = i; k < model.getNumRows() - 1; k++) {
        paddingSpaces = paddingSpaces + "  ";
      }
      if (currentRowToString.substring(currentRowToString.length() - 1).equals(" ")) {
        pyramidView
            .add(paddingSpaces + currentRowToString.substring(1, currentRowToString.length() - 1));
      } else {
        pyramidView
            .add(paddingSpaces + currentRowToString.substring(1, currentRowToString.length()));
      }
    }
    List<?> drawCards = model.getDrawCards();
    String drawCardView = "Draw:";
    for (Object dc : drawCards) {
      drawCardView = drawCardView + " " + dc.toString() + ",";
    }

    pyramidView.add(drawCardView.substring(0, drawCardView.length() - 1));

    String output = "";
    for (String row : pyramidView) {
      row = "." + row;
      row = row.trim();
      output = output + row.substring(1, row.length()) + "\n";
    }

    return output.substring(0, output.length() - 1);
  }

  @Override
  public void render() throws IOException {
    this.out.append(this.toString());
  }
  
}
