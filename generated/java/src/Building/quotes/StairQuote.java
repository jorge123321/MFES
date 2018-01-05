package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class StairQuote {
  private static int hc = 0;
  private static StairQuote instance = null;

  public StairQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static StairQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new StairQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof StairQuote;
  }

  public String toString() {

    return "<Stair>";
  }
}
