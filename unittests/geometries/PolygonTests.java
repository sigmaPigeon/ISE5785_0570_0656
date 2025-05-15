package geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import primitives.*;

/**
 * Testing Polygons
 * @author Dan
 */
class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private static final double DELTA = 0.000001;

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   void testConstructor() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
   void testFindIntersections(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is inside the polygon
        Polygon polygon = new Polygon(new Point(0, 0, 1),
                                       new Point(1, 0, 0),
                                       new Point(0, 1, 0),
                                       new Point(-1, 1, 1));
        Point p01 = new Point(0.5, 0.5, 0);
        Vector v01 = new Vector(0, 0.5, 0);
        assertNull(polygon.findIntersections(new Ray(p01, v01)), "Ray's line out of polygon");

        // TC02: Ray intersects the edge of the polygon
        Point p02 = new Point(2, 2, 0);
        Vector v02 = new Vector(-1, -1, 0);
        assertNull(polygon.findIntersections(new Ray(p02, v02)), "Ray's line crosses polygon");

        // TC03: Ray intersects the vertex of the polygon
        Point p03 = new Point(0, 3, 0);
        Vector v03 = new Vector(0, -2, 0);
        assertNull(polygon.findIntersections(new Ray(p03, v03)), "Ray's line crosses polygon");

        //============= Boundary Values Tests ==================
        // TC10: Ray starts at the edge of the polygon
        Point p10 = new Point(1, 1, 0);
        Vector v10 = new Vector(1, 1, 0);
        assertNull(polygon.findIntersections(new Ray(p10, v10)), "Ray's line crosses polygon");

        // TC11: Ray starts at the vertex of the polygon
        Point p11 = new Point(0, 2, 0);
        Vector v11 = new Vector(1, 1, 0);
        assertNull(polygon.findIntersections(new Ray(p11,v11)), "Ray's line crosses polygon");

        // TC12: Ray starts at the continuation of the edge polygon and goes inside
        Point p12 = new Point(0, -1, 2);
        Vector v12 = new Vector(-1, 0, 0);
        assertNull(polygon.findIntersections(new Ray(p12, v12)), "Ray's line crosses polygon");

    }
}
