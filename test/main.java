import java.io.InputStreamReader;
import java.util.Scanner;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

public class main {
  public static void main(String[] args) {
    PyramidSolitaireModel<Card> model = new BasicPyramidSolitaire();
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    controller.playGame(model, model.getDeck(), false, 9, 10);
  }
}
