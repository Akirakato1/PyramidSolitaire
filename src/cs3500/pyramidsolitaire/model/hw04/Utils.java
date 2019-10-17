package cs3500.pyramidsolitaire.model.hw04;

/**
 * Utility class with some mathematical functions.
 * 
 * @author Akira Kato
 *
 */
public class Utils {

  /**
   * xor the two inputs.
   * 
   * @param one first boolean.
   * @param two second boolean.
   * @return xor result
   */
  static boolean xor(boolean one, boolean two) {
    return (one && !two) || (two && !one);
  }

  /**
   * Domain: integer, Range: [1:infinity].
   * 
   * @param n integer.
   * @return truncated integer.
   */
  static int truncNat(int n) {
    if (n < 2) {
      return 1;
    } else {
      return n;
    }
  }
}
