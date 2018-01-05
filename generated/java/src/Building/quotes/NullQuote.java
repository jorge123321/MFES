package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class NullQuote {
  private static int hc = 0;
  private static NullQuote instance = null;

  public NullQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static NullQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new NullQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof NullQuote;
  }

  public String toString() {

    return "<Null>";
  }
}
