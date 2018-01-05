package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class WcQuote {
  private static int hc = 0;
  private static WcQuote instance = null;

  public WcQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static WcQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new WcQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof WcQuote;
  }

  public String toString() {

    return "<Wc>";
  }
}
