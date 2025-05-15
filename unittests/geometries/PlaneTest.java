package geometries;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
/**
 * Unit tests for {@link geometries.Plane}.
 * This class contains tests for the constructor, normal vector calculation, and intersection
 * of a plane with a ray.
 * <p>
 * The tests cover:
 * <ul>
 *     <li>Constructor with three points</li>
 *     <li>Normal vector calculation</li>
 *     <li>Intersection with a ray</li>
 * </ul>
 */
class PlaneTest {
    private static final double DELTA = 0.000001;    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Vector)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal vector of the constructor of a plane using 3 points
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        Plane plane = new Plane(p1, p2, p3);
        assertNotNull(plane, "Plane constructor failed");

        // Verify the normal vector is orthogonal to the plane
        Vector normal = plane.getNormal(p1);
        assertEquals(1, normal.length(), DELTA, "Plane's normal is not a unit vector");
        assertEquals(0, normal.dotProduct(p2.subtract(p1)), DELTA, "Plane's normal is not orthogonal to the first edge");
        assertEquals(0, normal.dotProduct(p3.subtract(p1)), DELTA, "Plane's normal is not orthogonal to the second edge");

        //============== Boundary Values Tests ==================
        //TC10: Test the constructor of a plane using the first 2 points that are coalesced
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(1, 1, 1));
        }, "Plane constructor should throw an exception for coalesced points");

        //TC11: Test the constructor of a plane using the last 2 points that are coalesced
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(1, 1, 1));
        }, "Plane constructor should throw an exception for coalesced points");

        //TC12: Test the constructor of a plane using the first and last points that are coalesced
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(0, 0, 0));
        }, "Plane constructor should throw an exception for coalesced points");

        //TC13: Test the constructor of a plane using 3 points that are coalesced
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(0, 0, 0), new Point(0, 0, 0), new Point(0, 0, 0));
        }, "Plane constructor should throw an exception for coalesced points");

        //TC14: Test the constructor of a plane using 3 points that are collinear
        assertThrows(IllegalArgumentException.class, () -> {
            new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2));
        }, "Plane constructor should throw an exception for collinear points");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal vector of a plane from a certain point
        Plane plane = new Plane(new Point(0, 0, 0), new Vector(1, 0, 0), new Vector(0, 1, 0));
        // Verify the normal vector is orthogonal to the plane
        Vector expectedNormal1 = new Vector(0, 0, 1);
        // Verify the normal vector is orthogonal to the plane since the vector could be normalized to both directions
        Vector expectedNormal2 = new Vector(0, 0, -1);
        // Verify if one of the normal vectors is a unit vector
        assertEquals(expectedNormal1, plane.getNormal(new Point(0,0 ,0 )), "Plane normal is not correct");
    }
     /*
        * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the intersection of a ray with a plane
        Plane plane = new Plane(new Point(0, 1, 0), new Vector(0, 0, 1));
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(0, -1, -1));
        Point expectedIntersection = new Point(1, 0, 0);
        List<Point> intersections = plane.findIntersections(ray);
        assertEquals(1,intersections.size(), "Plane intersection failed");
        assertEquals(expectedIntersection, intersections.get(0), "Plane intersection point is not correct");

        // TC02: Test the intersection of a ray that does not intersect with the plane
        Ray nonIntersectingRay = new Ray(new Point(1, 1, 1), new Vector(0, 1, 1));
        List<Point> nonIntersectingIntersections = plane.findIntersections(nonIntersectingRay);
        assertNull(nonIntersectingIntersections, "Plane intersection should be null for non-intersecting ray");

        //============== Boundary Values Tests ==================
        // TC10: Test the intersection of a ray that is parallel to the plane
        Ray parallelRay = new Ray(new Point(1, 1, 1), new Vector(1, 1, 0));
        List<Point> parallelIntersections = plane.findIntersections(parallelRay);
        assertNull(parallelIntersections, "Plane intersection should be null for parallel ray");

        // TC11: Test the intersection of a ray that is contained in the plane
        Ray containedRay = new Ray(new Point(0, 0, 0), new Vector(0, 1, 0));
        List<Point> containedIntersections = plane.findIntersections(containedRay);
        assertNull(containedIntersections, "Plane intersection should be null for contained ray");

        // TC12: Test the intersection of a vertical ray that is before the plane
        Ray beforePlaneRay = new Ray(new Point(-1, -1, -1), new Vector(0, 0, 2));
        List<Point> beforePlaneIntersections = plane.findIntersections(beforePlaneRay);
        assertEquals(1, beforePlaneIntersections.size(), "Plane intersection failed");
        assertEquals(new Point(-1, -1, 0), beforePlaneIntersections.get(0), "Plane intersection point is not correct");

        // TC13: Test the intersection of a vertical ray that is after the plane
        Ray afterPlaneRay = new Ray(new Point(1, 1, 1), new Vector(0, 0, -2));
        List<Point> afterPlaneIntersections = plane.findIntersections(afterPlaneRay);
        assertEquals(1, afterPlaneIntersections.size(), "Plane intersection failed");
        assertEquals(new Point(1, 1, 0), afterPlaneIntersections.get(0), "Plane intersection point is not correct");

        // TC14: Test the intersection of a vertical ray that is on the plane
        Ray onPlaneRay = new Ray(new Point(1, 1, 0), new Vector(0, 0, 1));
        List<Point> onPlaneIntersections = plane.findIntersections(onPlaneRay);
        assertNull(onPlaneIntersections, "Plane intersection should be null for on-plane ray");

        // TC15: Test the intersection of a ray that is on the plane
        Ray onPlaneRay2 = new Ray(new Point(1, 1, 0), new Vector(0, 1, 2));
        List<Point> onPlaneIntersections2 = plane.findIntersections(onPlaneRay2);
        assertNull(onPlaneIntersections2, "Plane intersection should be null for on-plane ray");

        // TC16: Test the intersection of a ray that starts on the starting point of the plane
        Ray startOnPlaneRay = new Ray(new Point(0, 1, 0), new Vector(1, 1, 1));
        List<Point> startOnPlaneIntersections = plane.findIntersections(startOnPlaneRay);
        assertNull(startOnPlaneIntersections, "Plane intersection should be null for ray starting on plane");

    }
}

