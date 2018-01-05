package Building;

import Building.quotes.*;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Building {
  public static final Number min_lines = 3L;
  public static final Number min_columns = 3L;
  public static final Number min_floors = 1L;
  public VDMSet points = SetUtil.set();
  public PointOfInterest origin;
  public PointOfInterest destination;
  public PointOfInterest s;
  public VDMSet C = SetUtil.set();
  public VDMSet Q = SetUtil.set();
  public VDMMap dist = MapUtil.map();
  public VDMMap pred = MapUtil.map();
  public VDMSet edges = SetUtil.set();
  public VDMSet weights = SetUtil.set(1L);
  public VDMSet graph = SetUtil.set();
  public Number max_lines;
  public Number max_columns;
  public Number max_floors;

  public void cg_init_Building_1(final Number x, final Number y, final Number z) {

    max_lines = x.longValue() - 1L;
    max_columns = y.longValue() - 1L;
    max_floors = z.longValue() - 1L;
    long toVar_3 = x.longValue() - 1L;

    for (Long i = 0L; i <= toVar_3; i++) {
      long toVar_2 = y.longValue() - 1L;

      for (Long j = 0L; j <= toVar_2; j++) {
        long toVar_1 = z.longValue() - 1L;

        for (Long k = 0L; k <= toVar_1; k++) {
          PointOfInterest point =
              new PointOfInterest(i, j, k, NullQuote.getInstance());
          addPoint(point);
        }
      }
    }
    return;
  }

  public VDMSet getPoints() {

    return Utils.copy(points);
  }

  public Building(final Number x, final Number y, final Number z) {

    cg_init_Building_1(x, y, z);
  }

  public Boolean sameCoords(final PointOfInterest p1, final PointOfInterest p2) {

    Boolean andResult_2 = false;

    if (Utils.equals(p1.GetX(), p2.GetX())) {
      Boolean andResult_3 = false;

      if (Utils.equals(p1.GetY(), p2.GetY())) {
        if (Utils.equals(p1.GetZ(), p2.GetZ())) {
          andResult_3 = true;
        }
      }

      if (andResult_3) {
        andResult_2 = true;
      }
    }

    if (andResult_2) {
      return true;

    } else {
      return false;
    }
  }

  public VDMSeq makePath() {

    VDMSeq pathToDest = SeqUtil.seq();
    Boolean arrivedOrigin = false;
    PointOfInterest tempPoint = destination;
    Number count = 0L;
    Number maxIterations = MapUtil.dom(Utils.copy(pred)).size();
    pathToDest = SeqUtil.conc(Utils.copy(pathToDest), SeqUtil.seq(tempPoint));
    Boolean whileCond_1 = true;
    while (whileCond_1) {
      Boolean andResult_7 = false;

      if (!(arrivedOrigin)) {
        if (count.longValue() < maxIterations.longValue()) {
          andResult_7 = true;
        }
      }

      whileCond_1 = andResult_7;

      if (!(whileCond_1)) {
        break;
      }

      {
        for (Iterator iterator_22 = MapUtil.dom(Utils.copy(pred)).iterator();
            iterator_22.hasNext();
            ) {
          PointOfInterest p1 = (PointOfInterest) iterator_22.next();
          if (sameCoords(tempPoint, p1)) {
            if (sameCoords(p1, origin)) {
              arrivedOrigin = true;

            } else {
              pathToDest =
                  SeqUtil.conc(
                      Utils.copy(pathToDest), SeqUtil.seq(((PointOfInterest) Utils.get(pred, p1))));
              tempPoint = ((PointOfInterest) Utils.get(pred, p1));
            }
          }
        }
        count = count.longValue() + 1L;
      }
    }

    Boolean andResult_8 = false;

    if (Utils.equals(count, maxIterations)) {
      if (!(arrivedOrigin)) {
        andResult_8 = true;
      }
    }

    if (andResult_8) {
      return SeqUtil.seq();

    } else {
      return Utils.copy(pathToDest);
    }
  }

  public Tuple startDijkstra() {

    for (Iterator iterator_23 = points.iterator(); iterator_23.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_23.next();
      VDMSet setCompResult_1 = SetUtil.set();
      VDMSet set_3 = getNeighbours(p);
      for (Iterator iterator_3 = set_3.iterator(); iterator_3.hasNext(); ) {
        PointOfInterest e2 = ((PointOfInterest) iterator_3.next());
        if (!(Utils.equals(e2.GetDesc(), WallQuote.getInstance()))) {
          setCompResult_1.add(SetUtil.set(p, e2));
        }
      }
      {
        final VDMSet tedges = Utils.copy(setCompResult_1);
        {
          edges = SetUtil.union(Utils.copy(edges), Utils.copy(tedges));
        }
      }
    }
    VDMSet setCompResult_2 = SetUtil.set();
    VDMSet set_4 = Utils.copy(edges);
    for (Iterator iterator_4 = set_4.iterator(); iterator_4.hasNext(); ) {
      VDMSet e = ((VDMSet) iterator_4.next());
      setCompResult_2.add(Utils.copy(e));
    }
    {
      final VDMSet tedges = Utils.copy(setCompResult_2);
      VDMSet setCompResult_3 = SetUtil.set();
      VDMSet set_5 = Utils.copy(edges);
      for (Iterator iterator_5 = set_5.iterator(); iterator_5.hasNext(); ) {
        VDMSet e = ((VDMSet) iterator_5.next());
        VDMSet set_6 = Utils.copy(weights);
        for (Iterator iterator_6 = set_6.iterator(); iterator_6.hasNext(); ) {
          Number w = ((Number) iterator_6.next());
          setCompResult_3.add(new Graph(e, w));
        }
      }
      {
        final VDMSet tgraph = Utils.copy(setCompResult_3);
        {
          graph = Utils.copy(tgraph);
        }
      }
    }

    dist = MapUtil.map();
    pred = MapUtil.map();
    C = SetUtil.set();
    Q = SetUtil.set();
    return dijkstra(Utils.copy(graph));
  }

  public Tuple dijkstra(final VDMSet wgraph) {

    s = origin;
    Q = Utils.copy(points);
    C = SetUtil.union(Utils.copy(C), SetUtil.set(s));
    dist = MapUtil.override(Utils.copy(dist), MapUtil.map(new Maplet(s, 0L)));
    pred = MapUtil.override(Utils.copy(dist), MapUtil.map(new Maplet(s, s)));
    Boolean whileCond_2 = true;
    while (whileCond_2) {
      whileCond_2 = !(Utils.empty(SetUtil.intersect(Utils.copy(C), Utils.copy(Q))));
      if (!(whileCond_2)) {
        break;
      }

      {
        PointOfInterest u = null;
        Boolean success_1 = false;
        VDMSet set_1 = SetUtil.intersect(Utils.copy(C), Utils.copy(Q));
        for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext() && !(success_1); ) {
          u = ((PointOfInterest) iterator_1.next());
          Boolean forAllExpResult_1 = true;
          VDMSet set_7 = SetUtil.intersect(Utils.copy(C), Utils.copy(Q));
          for (Iterator iterator_7 = set_7.iterator();
              iterator_7.hasNext() && forAllExpResult_1;
              ) {
            PointOfInterest u1 = ((PointOfInterest) iterator_7.next());
            forAllExpResult_1 =
                ((Number) Utils.get(dist, u)).longValue()
                    <= ((Number) Utils.get(dist, u1)).longValue();
          }
          success_1 = forAllExpResult_1;
        }
        if (!(success_1)) {
          throw new RuntimeException("Let Be St found no applicable bindings");
        }

        {
          Q = SetUtil.diff(Utils.copy(Q), SetUtil.set(u));
          for (Iterator iterator_24 = Q.iterator(); iterator_24.hasNext(); ) {
            PointOfInterest v = (PointOfInterest) iterator_24.next();
            if (!(Utils.equals(v.GetDesc(), WallQuote.getInstance()))) {
              if (SetUtil.inSet(u, getNeighbours(v))) {
                {
                  Graph uv = null;
                  Boolean success_2 = false;
                  VDMSet set_2 = Utils.copy(wgraph);
                  for (Iterator iterator_2 = set_2.iterator();
                      iterator_2.hasNext() && !(success_2);
                      ) {
                    uv = ((Graph) iterator_2.next());
                    success_2 = Utils.equals(uv.e, SetUtil.set(u, v));
                  }
                  if (!(success_2)) {
                    throw new RuntimeException("Let Be St found no applicable bindings");
                  }

                  {
                    if (uv.w.longValue() > 0L) {
                      {
                        final Number length =
                            ((Number) Utils.get(dist, u)).longValue() + uv.w.longValue();
                        {
                          if (SetUtil.inSet(v, C)) {
                            if (length.longValue() < ((Number) Utils.get(dist, v)).longValue()) {
                              dist =
                                  MapUtil.override(
                                      Utils.copy(dist), MapUtil.map(new Maplet(v, length)));
                              pred =
                                  MapUtil.override(Utils.copy(pred), MapUtil.map(new Maplet(v, u)));
                            }

                          } else {
                            dist =
                                MapUtil.override(
                                    Utils.copy(dist), MapUtil.map(new Maplet(v, length)));
                            pred =
                                MapUtil.override(Utils.copy(pred), MapUtil.map(new Maplet(v, u)));
                            C = SetUtil.union(Utils.copy(C), SetUtil.set(v));
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    return Tuple.mk_(Utils.copy(C), Utils.copy(dist), Utils.copy(pred));
  }

  public void addPoint(final PointOfInterest P) {

    points = SetUtil.union(Utils.copy(points), SetUtil.set(P));
    return;
  }

  public Boolean addOrigin(final Number x, final Number y, final Number z) {

    for (Iterator iterator_25 = points.iterator(); iterator_25.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_25.next();
      Boolean andResult_13 = false;

      if (Utils.equals(p.GetX(), x)) {
        Boolean andResult_14 = false;

        if (Utils.equals(p.GetY(), y)) {
          if (Utils.equals(p.GetZ(), z)) {
            andResult_14 = true;
          }
        }

        if (andResult_14) {
          andResult_13 = true;
        }
      }

      if (andResult_13) {
        p.changePoint(x, y, z, OriginQuote.getInstance());
        origin = p;
        return true;
      }
    }
    return false;
  }

  public Boolean addDestination(final Number x, final Number y, final Number z) {

    for (Iterator iterator_26 = points.iterator(); iterator_26.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_26.next();
      Boolean andResult_16 = false;

      if (Utils.equals(p.GetX(), x)) {
        Boolean andResult_17 = false;

        if (Utils.equals(p.GetY(), y)) {
          if (Utils.equals(p.GetZ(), z)) {
            andResult_17 = true;
          }
        }

        if (andResult_17) {
          andResult_16 = true;
        }
      }

      if (andResult_16) {
        p.changePoint(x, y, z, DestinationQuote.getInstance());
        destination = p;
        return true;
      }
    }
    return false;
  }

  public Boolean addType(final Number x, final Number y, final Number z, final Object type) {

    for (Iterator iterator_27 = points.iterator(); iterator_27.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_27.next();
      Boolean andResult_18 = false;

      if (Utils.equals(p.GetX(), x)) {
        Boolean andResult_19 = false;

        if (Utils.equals(p.GetY(), y)) {
          if (Utils.equals(p.GetZ(), z)) {
            andResult_19 = true;
          }
        }

        if (andResult_19) {
          andResult_18 = true;
        }
      }

      if (andResult_18) {
        p.changePoint(x, y, z, type);
        return true;
      }
    }
    return false;
  }

  public Boolean changeOrigin(final Number x, final Number y, final Number z) {

    Boolean result = false;
    for (Iterator iterator_28 = points.iterator(); iterator_28.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_28.next();
      Boolean andResult_21 = false;

      if (Utils.equals(p.GetX(), x)) {
        Boolean andResult_22 = false;

        if (Utils.equals(p.GetY(), y)) {
          if (Utils.equals(p.GetZ(), z)) {
            andResult_22 = true;
          }
        }

        if (andResult_22) {
          andResult_21 = true;
        }
      }

      if (andResult_21) {
        p.changePoint(x, y, z, OriginQuote.getInstance());
        origin = p;
        result = true;
      }
    }
    if (result) {
      for (Iterator iterator_29 = points.iterator(); iterator_29.hasNext(); ) {
        PointOfInterest p = (PointOfInterest) iterator_29.next();
        Boolean andResult_23 = false;

        if (Utils.equals(p.GetDesc(), OriginQuote.getInstance())) {
          Boolean andResult_24 = false;

          if (!(Utils.equals(p.GetX(), x))) {
            Boolean andResult_25 = false;

            if (!(Utils.equals(p.GetY(), y))) {
              if (!(Utils.equals(p.GetZ(), z))) {
                andResult_25 = true;
              }
            }

            if (andResult_25) {
              andResult_24 = true;
            }
          }

          if (andResult_24) {
            andResult_23 = true;
          }
        }

        if (andResult_23) {
          p.changePoint(x, y, z, NullQuote.getInstance());
        }
      }
    }

    return result;
  }

  public Boolean changeDestination(final Number x, final Number y, final Number z) {

    Boolean result = false;
    for (Iterator iterator_30 = points.iterator(); iterator_30.hasNext(); ) {
      PointOfInterest p = (PointOfInterest) iterator_30.next();
      Boolean andResult_27 = false;

      if (Utils.equals(p.GetX(), x)) {
        Boolean andResult_28 = false;

        if (Utils.equals(p.GetY(), y)) {
          if (Utils.equals(p.GetZ(), z)) {
            andResult_28 = true;
          }
        }

        if (andResult_28) {
          andResult_27 = true;
        }
      }

      if (andResult_27) {
        p.changePoint(x, y, z, DestinationQuote.getInstance());
        destination = p;
        result = true;
      }
    }
    if (result) {
      for (Iterator iterator_31 = points.iterator(); iterator_31.hasNext(); ) {
        PointOfInterest p = (PointOfInterest) iterator_31.next();
        Boolean andResult_29 = false;

        if (Utils.equals(p.GetDesc(), DestinationQuote.getInstance())) {
          Boolean andResult_30 = false;

          if (!(Utils.equals(p.GetX(), x))) {
            Boolean andResult_31 = false;

            if (!(Utils.equals(p.GetY(), y))) {
              if (!(Utils.equals(p.GetZ(), z))) {
                andResult_31 = true;
              }
            }

            if (andResult_31) {
              andResult_30 = true;
            }
          }

          if (andResult_30) {
            andResult_29 = true;
          }
        }

        if (andResult_29) {
          p.changePoint(x, y, z, NullQuote.getInstance());
        }
      }
    }

    return result;
  }

  public VDMSet getNeighbours(final PointOfInterest point) {

    VDMSet neighboursSet = SetUtil.set();
    for (Iterator iterator_32 = points.iterator(); iterator_32.hasNext(); ) {
      PointOfInterest p1 = (PointOfInterest) iterator_32.next();
      Boolean andResult_32 = false;

      if (Utils.equals(p1.GetX(), point.GetX().longValue() - 1L)) {
        Boolean andResult_33 = false;

        if (Utils.equals(p1.GetY(), point.GetY())) {
          if (Utils.equals(p1.GetZ(), point.GetZ())) {
            andResult_33 = true;
          }
        }

        if (andResult_33) {
          andResult_32 = true;
        }
      }

      if (andResult_32) {
        neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));

      } else {
        Boolean andResult_34 = false;

        if (Utils.equals(p1.GetX(), point.GetX().longValue() + 1L)) {
          Boolean andResult_35 = false;

          if (Utils.equals(p1.GetY(), point.GetY())) {
            if (Utils.equals(p1.GetZ(), point.GetZ())) {
              andResult_35 = true;
            }
          }

          if (andResult_35) {
            andResult_34 = true;
          }
        }

        if (andResult_34) {
          neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));

        } else {
          Boolean andResult_36 = false;

          if (Utils.equals(p1.GetX(), point.GetX())) {
            Boolean andResult_37 = false;

            if (Utils.equals(p1.GetY(), point.GetY().longValue() - 1L)) {
              if (Utils.equals(p1.GetZ(), point.GetZ())) {
                andResult_37 = true;
              }
            }

            if (andResult_37) {
              andResult_36 = true;
            }
          }

          if (andResult_36) {
            neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));

          } else {
            Boolean andResult_38 = false;

            if (Utils.equals(p1.GetX(), point.GetX())) {
              Boolean andResult_39 = false;

              if (Utils.equals(p1.GetY(), point.GetY().longValue() + 1L)) {
                if (Utils.equals(p1.GetZ(), point.GetZ())) {
                  andResult_39 = true;
                }
              }

              if (andResult_39) {
                andResult_38 = true;
              }
            }

            if (andResult_38) {
              neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));
            }
          }
        }
      }

      Boolean orResult_1 = false;

      if (Utils.equals(point.GetDesc(), StairQuote.getInstance())) {
        orResult_1 = true;
      } else {
        orResult_1 = Utils.equals(point.GetDesc(), ElevatorQuote.getInstance());
      }

      if (orResult_1) {
        Boolean andResult_40 = false;

        if (Utils.equals(p1.GetX(), point.GetX())) {
          Boolean andResult_41 = false;

          if (Utils.equals(p1.GetY(), point.GetY())) {
            if (Utils.equals(p1.GetZ(), point.GetZ().longValue() + 1L)) {
              andResult_41 = true;
            }
          }

          if (andResult_41) {
            andResult_40 = true;
          }
        }

        if (andResult_40) {
          neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));

        } else {
          Boolean andResult_42 = false;

          if (Utils.equals(p1.GetX(), point.GetX())) {
            Boolean andResult_43 = false;

            if (Utils.equals(p1.GetY(), point.GetY())) {
              if (Utils.equals(p1.GetZ(), point.GetZ().longValue() - 1L)) {
                andResult_43 = true;
              }
            }

            if (andResult_43) {
              andResult_42 = true;
            }
          }

          if (andResult_42) {
            neighboursSet = SetUtil.union(Utils.copy(neighboursSet), SetUtil.set(p1));
          }
        }
      }
    }
    return Utils.copy(neighboursSet);
  }

  public VDMSeq getPathToClosestType(final Object type) {

    for (Iterator iterator_33 = MapUtil.dom(Utils.copy(dist)).iterator(); iterator_33.hasNext(); ) {
      PointOfInterest p1 = (PointOfInterest) iterator_33.next();
      if (Utils.equals(p1.GetDesc(), type)) {
        destination = p1;
        return makePath();
      }
    }
    return SeqUtil.seq();
  }

  public Building() {}

  public static Boolean IsShortestPath(
      final VDMMap dist_1,
      final VDMMap pred_1,
      final VDMSet C_1,
      final PointOfInterest s_1,
      final VDMSet graph_1,
      final VDMSet points_1) {

    Boolean andResult_46 = false;

    if (DefinesShortestDist(
        Utils.copy(dist_1), Utils.copy(pred_1), Utils.copy(C_1), s_1, Utils.copy(graph_1))) {
      if (SetOfLinkedVertices(Utils.copy(C_1), s_1, Utils.copy(graph_1), Utils.copy(points_1))) {
        andResult_46 = true;
      }
    }

    return andResult_46;
  }

  public static Boolean DefinesShortestDist(
      final VDMMap dist,
      final VDMMap pred,
      final VDMSet C,
      final PointOfInterest s,
      final VDMSet graph) {

    Boolean andResult_47 = false;

    if (Utils.equals(((Number) Utils.get(dist, s)), 0L)) {
      Boolean forAllExpResult_2 = true;
      VDMSet set_9 = SetUtil.diff(Utils.copy(C), SetUtil.set(s));
      for (Iterator iterator_9 = set_9.iterator(); iterator_9.hasNext() && forAllExpResult_2; ) {
        PointOfInterest u = ((PointOfInterest) iterator_9.next());
        Boolean andResult_48 = false;

        Boolean existsExpResult_2 = false;
        VDMSet set_10 = Utils.copy(C);
        for (Iterator iterator_10 = set_10.iterator();
            iterator_10.hasNext() && !(existsExpResult_2);
            ) {
          PointOfInterest v = ((PointOfInterest) iterator_10.next());
          Boolean andResult_49 = false;

          if (Utils.equals(((PointOfInterest) Utils.get(pred, u)), v)) {
            Boolean andResult_50 = false;

            if (neighbour(Utils.copy(graph), u, v)) {
              Boolean letBeStExp_1 = null;
              Graph tup = null;
              Boolean success_3 = false;
              VDMSet set_11 = Utils.copy(graph);
              for (Iterator iterator_11 = set_11.iterator();
                  iterator_11.hasNext() && !(success_3);
                  ) {
                tup = ((Graph) iterator_11.next());
                success_3 = Utils.equals(tup.e, SetUtil.set(u, v));
              }
              if (!(success_3)) {
                throw new RuntimeException("Let Be St found no applicable bindings");
              }

              letBeStExp_1 =
                  Utils.equals(
                      ((Number) Utils.get(dist, u)),
                      ((Number) Utils.get(dist, v)).longValue() + tup.w.longValue());
              if (letBeStExp_1) {
                andResult_50 = true;
              }
            }

            if (andResult_50) {
              andResult_49 = true;
            }
          }

          existsExpResult_2 = andResult_49;
        }
        if (existsExpResult_2) {
          Boolean forAllExpResult_3 = true;
          VDMSet set_12 = Utils.copy(C);
          for (Iterator iterator_12 = set_12.iterator();
              iterator_12.hasNext() && forAllExpResult_3;
              ) {
            PointOfInterest u1 = ((PointOfInterest) iterator_12.next());
            for (Iterator iterator_13 = set_12.iterator();
                iterator_13.hasNext() && forAllExpResult_3;
                ) {
              PointOfInterest v = ((PointOfInterest) iterator_13.next());
              Boolean orResult_2 = false;

              if (!(neighbour(Utils.copy(graph), u1, v))) {
                orResult_2 = true;
              } else {
                Boolean letBeStExp_2 = null;
                Graph tup = null;
                Boolean success_4 = false;
                VDMSet set_13 = Utils.copy(graph);
                for (Iterator iterator_14 = set_13.iterator();
                    iterator_14.hasNext() && !(success_4);
                    ) {
                  tup = ((Graph) iterator_14.next());
                  success_4 = Utils.equals(tup.e, SetUtil.set(u1, v));
                }
                if (!(success_4)) {
                  throw new RuntimeException("Let Be St found no applicable bindings");
                }

                letBeStExp_2 =
                    ((Number) Utils.get(dist, u1)).longValue()
                        <= ((Number) Utils.get(dist, v)).longValue() + tup.w.longValue();
                orResult_2 = letBeStExp_2;
              }

              forAllExpResult_3 = orResult_2;
            }
          }
          if (forAllExpResult_3) {
            andResult_48 = true;
          }
        }

        forAllExpResult_2 = andResult_48;
      }
      if (forAllExpResult_2) {
        andResult_47 = true;
      }
    }

    return andResult_47;
  }

  public static Boolean SetOfLinkedVertices(
      final VDMSet C, final PointOfInterest s, final VDMSet graph, final VDMSet points) {

    Boolean forAllExpResult_4 = true;
    VDMSet set_14 = Utils.copy(C);
    for (Iterator iterator_15 = set_14.iterator(); iterator_15.hasNext() && forAllExpResult_4; ) {
      PointOfInterest u = ((PointOfInterest) iterator_15.next());
      Boolean andResult_51 = false;

      Boolean forAllExpResult_5 = true;
      VDMSet set_15 = Utils.copy(points);
      for (Iterator iterator_16 = set_15.iterator(); iterator_16.hasNext() && forAllExpResult_5; ) {
        PointOfInterest v = ((PointOfInterest) iterator_16.next());
        Boolean orResult_3 = false;

        if (!(neighbour(Utils.copy(graph), u, v))) {
          orResult_3 = true;
        } else {
          orResult_3 = SetUtil.inSet(v, C);
        }

        forAllExpResult_5 = orResult_3;
      }
      if (forAllExpResult_5) {
        Boolean forAllExpResult_6 = true;
        VDMSet set_16 = SetUtil.diff(Utils.copy(C), SetUtil.set(s));
        for (Iterator iterator_17 = set_16.iterator();
            iterator_17.hasNext() && forAllExpResult_6;
            ) {
          PointOfInterest u1 = ((PointOfInterest) iterator_17.next());
          Boolean existsExpResult_3 = false;
          VDMSet set_17 = Utils.copy(C);
          for (Iterator iterator_18 = set_17.iterator();
              iterator_18.hasNext() && !(existsExpResult_3);
              ) {
            PointOfInterest v = ((PointOfInterest) iterator_18.next());
            existsExpResult_3 = neighbour(Utils.copy(graph), u1, v);
          }
          forAllExpResult_6 = existsExpResult_3;
        }
        if (forAllExpResult_6) {
          andResult_51 = true;
        }
      }

      forAllExpResult_4 = andResult_51;
    }
    return forAllExpResult_4;
  }

  public static Boolean neighbour(
      final VDMSet wg, final PointOfInterest i, final PointOfInterest j) {

    Boolean existsExpResult_4 = false;
    VDMSet set_18 = Utils.copy(wg);
    for (Iterator iterator_19 = set_18.iterator();
        iterator_19.hasNext() && !(existsExpResult_4);
        ) {
      Graph tup = ((Graph) iterator_19.next());
      Boolean andResult_52 = false;

      if (Utils.equals(tup.e, SetUtil.set(i, j))) {
        Boolean andResult_53 = false;

        if (tup.w.longValue() > 0L) {
          if (!(Utils.equals(j.description, WallQuote.getInstance()))) {
            andResult_53 = true;
          }
        }

        if (andResult_53) {
          andResult_52 = true;
        }
      }

      existsExpResult_4 = andResult_52;
    }
    return existsExpResult_4;
  }

  public static Boolean hasOrigin(final VDMSet points) {

    Boolean existsExpResult_5 = false;
    VDMSet set_19 = Utils.copy(points);
    for (Iterator iterator_20 = set_19.iterator();
        iterator_20.hasNext() && !(existsExpResult_5);
        ) {
      PointOfInterest p = ((PointOfInterest) iterator_20.next());
      existsExpResult_5 = Utils.equals(p.description, OriginQuote.getInstance());
    }
    return existsExpResult_5;
  }

  public static Boolean hasDestination(final VDMSet points) {

    Boolean existsExpResult_6 = false;
    VDMSet set_20 = Utils.copy(points);
    for (Iterator iterator_21 = set_20.iterator();
        iterator_21.hasNext() && !(existsExpResult_6);
        ) {
      PointOfInterest p = ((PointOfInterest) iterator_21.next());
      existsExpResult_6 =
          Utils.equals(p.description, DestinationQuote.getInstance());
    }
    return existsExpResult_6;
  }

  public static Boolean validPosition(
      final Number x,
      final Number y,
      final Number z,
      final Number max_lines_1,
      final Number max_columns_1,
      final Number max_floors_1) {

    Boolean andResult_54 = false;

    if (x.longValue() >= 0L) {
      Boolean andResult_55 = false;

      if (x.longValue() <= max_lines_1.longValue()) {
        Boolean andResult_56 = false;

        if (y.longValue() >= 0L) {
          Boolean andResult_57 = false;

          if (y.longValue() <= max_columns_1.longValue()) {
            Boolean andResult_58 = false;

            if (z.longValue() >= 0L) {
              if (z.longValue() <= max_floors_1.longValue()) {
                andResult_58 = true;
              }
            }

            if (andResult_58) {
              andResult_57 = true;
            }
          }

          if (andResult_57) {
            andResult_56 = true;
          }
        }

        if (andResult_56) {
          andResult_55 = true;
        }
      }

      if (andResult_55) {
        andResult_54 = true;
      }
    }

    return andResult_54;
  }

  public String toString() {

    return "Building{"
        + "min_lines = "
        + Utils.toString(min_lines)
        + ", min_columns = "
        + Utils.toString(min_columns)
        + ", min_floors = "
        + Utils.toString(min_floors)
        + ", points := "
        + Utils.toString(points)
        + ", origin := "
        + Utils.toString(origin)
        + ", destination := "
        + Utils.toString(destination)
        + ", s := "
        + Utils.toString(s)
        + ", C := "
        + Utils.toString(C)
        + ", Q := "
        + Utils.toString(Q)
        + ", dist := "
        + Utils.toString(dist)
        + ", pred := "
        + Utils.toString(pred)
        + ", edges := "
        + Utils.toString(edges)
        + ", weights := "
        + Utils.toString(weights)
        + ", graph := "
        + Utils.toString(graph)
        + ", max_lines := "
        + Utils.toString(max_lines)
        + ", max_columns := "
        + Utils.toString(max_columns)
        + ", max_floors := "
        + Utils.toString(max_floors)
        + "}";
  }

  public static class Graph implements Record {
    public VDMSet e;
    public Number w;

    public Graph(final VDMSet _e, final Number _w) {

      e = _e != null ? Utils.copy(_e) : null;
      w = _w;
    }

    public boolean equals(final Object obj) {

      if (!(obj instanceof Graph)) {
        return false;
      }

      Graph other = ((Graph) obj);

      return (Utils.equals(e, other.e)) && (Utils.equals(w, other.w));
    }

    public int hashCode() {

      return Utils.hashCode(e, w);
    }

    public Graph copy() {

      return new Graph(e, w);
    }

    public String toString() {

      return "mk_Building`Graph" + Utils.formatFields(e, w);
    }
  }
  
  public static void main(String[] args) {
	  //teste 1 
	  System.out.println("Teste 1:\n");
	  Building b = new Building(5,5,2);
	  
	  b.addOrigin(4, 4, 0);
	  b.addDestination(4, 1, 0);
	  b.addType(4, 3, 0, WallQuote.getInstance());
	  
	  b.startDijkstra();
	  System.out.println(b.makePath());
	  
	  //teste 2 
	  System.out.println("Teste 2:\n");
	  Building b1 = new Building(5,5,2);
	  
	  b1.addOrigin(0, 0, 0);
	  b1.addType(0, 4, 0, WcQuote.getInstance());
	  b1.addType(0, 3, 0, WallQuote.getInstance());
	  
	  b1.startDijkstra();
	  System.out.println(b.getPathToClosestType(WcQuote.getInstance()));
	  
  }
}
