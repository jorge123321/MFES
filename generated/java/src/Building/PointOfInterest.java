package Building;

import Building.quotes.*;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PointOfInterest {
  public Position position;
  public Object description = NullQuote.getInstance();

  public void cg_init_PointOfInterest_1(
      final Number x, final Number y, final Number z, final Object desc) {

    position = new Position(x, y, z);
    description = desc;
  }

  public PointOfInterest(final Number x, final Number y, final Number z, final Object desc) {

    cg_init_PointOfInterest_1(x, y, z, desc);
  }

  public void SetX(final Number x) {

    position.x = x;
  }

  public void SetY(final Number y) {

    position.y = y;
  }

  public void SetZ(final Number z) {

    position.z = z;
  }

  public void SetDesc(final Object desc) {

    description = desc;
  }

  public Number GetX() {

    return position.x;
  }

  public Number GetY() {

    return position.y;
  }

  public Number GetZ() {

    return position.z;
  }

  public Object GetDesc() {

    return description;
  }

  public void changePoint(final Number x, final Number y, final Number z, final Object newDesc) {

    position.x = x;
    position.y = y;
    position.z = z;
    description = newDesc;
  }

  public PointOfInterest() {}

  public String toString() {

    return "PointOfInterest{"
        + "position := "
        + Utils.toString(position)
        + ", description := "
        + Utils.toString(description)
        + "}";
  }

  public static class Position implements Record {
    public Number x;
    public Number y;
    public Number z;

    public Position(final Number _x, final Number _y, final Number _z) {

      x = _x;
      y = _y;
      z = _z;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof Position)) {
        return false;
      }

      Position other = ((Position) obj);

      return (Utils.equals(x, other.x)) && (Utils.equals(y, other.y)) && (Utils.equals(z, other.z));
    }

    public int hashCode() {

      return Utils.hashCode(x, y, z);
    }

    public Position copy() {

      return new Position(x, y, z);
    }

    public String toString() {

      return "mk_PointOfInterest`Position" + Utils.formatFields(x, y, z);
    }
  }
}
