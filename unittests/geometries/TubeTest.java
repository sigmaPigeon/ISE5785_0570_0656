package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link geometries.Tube#getNormal(Point)}.
 * This class contains tests for the normal vector calculation of a tube.
 * <p>
 * The tests cover:
 * <ul>
 *     <li>Normals at points on the curved surface</li>
 *     <li>Normals at points on the axis</li>
 * </ul>
 */
class TubeTest {
    @Test
    /**
     * Test method for {@link Tube#getNormal(Point)}.
     * <p>
     * This method checks the correctness of the normal vector returned by the tube
     * for various points:
     * <ul>
     *     <li>On the curved surface</li>
     *     <li>On the axis</li>
     * </ul>
     * <p>
     * Each test case asserts that the returned normal vector matches the expected value.
     */
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a tube at a point on its surface
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1);
        Point pointOnSurface = new Point(1, 1, 0);
        Vector expectedNormal = new Vector(0, 1, 0);
        assertEquals(expectedNormal, tube.getNormal(pointOnSurface),
                "Tube's normal is not correct");
        //============= Boundary Values Tests ==================
        // TC10: Test the normal of a tube at a point on its axis
        // The normal at a point on the axis should be perpendicular to the axis
        Point pointOnAxis = new Point(0, 0.5, 0);
        Vector expectedNormalOnAxis = new Vector(0, 1, 0);
        assertEquals(expectedNormalOnAxis, tube.getNormal(pointOnAxis),
                "Tube's normal at a point on its axis is not correct");
    }


    @Test
    void testTubeIntersections() {
        // Tube along Z axis, radius 1, shifted from origin
        Tube tube = new Tube(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)), 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the tube at two points
        Ray ray1 = new Ray(new Point(3, 1, 2), new Vector(-1, 0, 0.5));
        List<Intersectable.Intersection> intersections1 = tube.calculateIntersectionsHelper(ray1);
        assertNotNull(intersections1, "Expected intersections");
        assertEquals(2, intersections1.size(), "Tube: Wrong number of intersections");

        // TC02: Ray intersects the tube at one point (tangent)
        Ray ray2 = new Ray(new Point(5, 5, 5), new Vector(1, 1, 1));
        List<Intersectable.Intersection> intersections2 = tube.calculateIntersectionsHelper(ray2);
        assertNull(intersections2, "Expected no intersection");
    }
}