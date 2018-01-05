package Building;

import java.util.*;
import Building.quotes.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TestBuilding extends MyTestCase {
  protected void assertFalse(final Boolean a) {

    return;
  }

  public void TestCreateBuilding() {

    Building b = new Building(5L, 5L, 2L);
    assertFalse(b.hasOrigin(b.points));
    assertFalse(b.hasDestination(b.points));
  }

  public void TestAddOrigin() {

    Building b = new Building(5L, 5L, 2L);
    Boolean result = false;
    assertFalse(b.hasOrigin(b.points));
    result = b.addOrigin(1L, 0L, 0L);
    assertTrue(b.hasOrigin(b.points));
  }

  public void TestAddDestination() {

    Building b = new Building(5L, 5L, 2L);
    Boolean result = false;
    assertFalse(b.hasDestination(b.points));
    result = b.addDestination(1L, 0L, 0L);
    assertTrue(b.hasDestination(b.points));
  }

  public void TestAddType() {

    Building b = new Building(5L, 5L, 2L);
    Boolean result = false;
    for (Iterator iterator_34 = b.points.iterator(); iterator_34.hasNext(); ) {
      PointOfInterest p1 = (PointOfInterest) iterator_34.next();
      Boolean andResult_59 = false;

      if (Utils.equals(p1.GetX(), 1L)) {
        Boolean andResult_60 = false;

        if (Utils.equals(p1.GetY(), 1L)) {
          if (Utils.equals(p1.GetZ(), 1L)) {
            andResult_60 = true;
          }
        }

        if (andResult_60) {
          andResult_59 = true;
        }
      }

      if (andResult_59) {
        assertEqual(((Object) p1.GetDesc()), NullQuote.getInstance());
        result =
            b.addType(p1.GetX(), p1.GetY(), p1.GetZ(), CoffeeQuote.getInstance());
        assertEqual(((Object) p1.GetDesc()), CoffeeQuote.getInstance());
      }
    }
  }

  public void TestMakePath() {

    Building b = new Building(5L, 5L, 2L);
    Boolean result = false;
    VDMSeq path = null;
    Tuple result2 = null;
    result = b.addOrigin(1L, 0L, 0L);
    result = b.addDestination(3L, 0L, 0L);
    result = b.addType(2L, 0L, 0L, WallQuote.getInstance());
    result2 = b.startDijkstra();
    path = b.makePath();
    assertEqual(path.size(), 5L);
    result = b.changeOrigin(4L, 4L, 0L);
    result = b.changeDestination(3L, 3L, 0L);
    result2 = b.startDijkstra();
    path = b.makePath();
    assertEqual(path.size(), 3L);
  }

  public void TestFindCoffeeNearby() {

    Building b = new Building(5L, 5L, 2L);
    Boolean result = false;
    VDMSeq path = null;
    Tuple result2 = null;
    result = b.addOrigin(1L, 0L, 0L);
    result = b.addDestination(3L, 0L, 0L);
    result2 = b.startDijkstra();
    for (Iterator iterator_35 = b.points.iterator(); iterator_35.hasNext(); ) {
      PointOfInterest p1 = (PointOfInterest) iterator_35.next();
      Boolean andResult_61 = false;

      if (Utils.equals(p1.GetX(), 2L)) {
        Boolean andResult_62 = false;

        if (Utils.equals(p1.GetY(), 2L)) {
          if (Utils.equals(p1.GetZ(), 0L)) {
            andResult_62 = true;
          }
        }

        if (andResult_62) {
          andResult_61 = true;
        }
      }

      if (andResult_61) {
        assertEqual(((Object) p1.GetDesc()), NullQuote.getInstance());
        result =
            b.addType(p1.GetX(), p1.GetY(), p1.GetZ(), CoffeeQuote.getInstance());
        assertEqual(((Object) p1.GetDesc()), CoffeeQuote.getInstance());
      }
    }
    path = b.getPathToClosestType(CoffeeQuote.getInstance());
    assertEqual(path.size(), 4L);
  }

  public static void main(String[] args) {

    new TestBuilding().TestCreateBuilding();
    new TestBuilding().TestAddOrigin();
    new TestBuilding().TestAddDestination();
    new TestBuilding().TestAddType();
    new TestBuilding().TestMakePath();
    new TestBuilding().TestFindCoffeeNearby();
  }

  public TestBuilding() {}

  public String toString() {

    return "TestBuilding{}";
  }
}
