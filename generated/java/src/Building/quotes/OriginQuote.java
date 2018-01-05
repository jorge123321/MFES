package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class OriginQuote {
  private static int hc = 0;
  private static OriginQuote instance = null;

  public OriginQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static OriginQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new OriginQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof OriginQuote;
  }

  public String toString() {

    return "<Origin>";
  }
}
