package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class WallQuote {
  private static int hc = 0;
  private static WallQuote instance = null;

  public WallQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static WallQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new WallQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof WallQuote;
  }

  public String toString() {

    return "<Wall>";
  }
}
