package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class CoffeeQuote {
  private static int hc = 0;
  private static CoffeeQuote instance = null;

  public CoffeeQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static CoffeeQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new CoffeeQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof CoffeeQuote;
  }

  public String toString() {

    return "<Coffee>";
  }
}
