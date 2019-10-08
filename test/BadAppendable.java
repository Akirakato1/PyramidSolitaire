import java.io.IOException;

/**
 * For testing IOexception.
 * 
 * @author Akira Kato
 *
 */
public class BadAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence input) throws IOException {
    throw new IOException("test");
  }

  @Override
  public Appendable append(char input) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Appendable append(CharSequence input1, int input2, int input3) throws IOException {
    // TODO Auto-generated method stub
    return null;
  }

}
