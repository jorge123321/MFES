package Building.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class ElevatorQuote {
  private static int hc = 0;
  private static ElevatorQuote instance = null;

  public ElevatorQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static ElevatorQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new ElevatorQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof ElevatorQuote;
  }

  public String toString() {

    return "<Elevator>";
  }
}
