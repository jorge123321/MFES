package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class DestinationQuote {
  private static int hc = 0;
  private static DestinationQuote instance = null;

  public DestinationQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static DestinationQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new DestinationQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof DestinationQuote;
  }

  public String toString() {

    return "<Destination>";
  }
}
