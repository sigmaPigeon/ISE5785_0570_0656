package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link geometries.Triangle#getNormal(Point)}.
 * This class contains tests for the normal vector calculation of a triangle.
 * <p>
 * The tests cover:
 * <ul>
 *     <li>Normal at a point on the triangle's surface</li>
 *     <li>Normal at a point outside the triangle</li>
 * </ul>
 */
class TriangleTest {
    private static final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a triangle at a point on its surface
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Point pointOnSurface = new Point(0.5, 0.5, 0);
        // Verify the normal vector is orthogonal to the triangle
        Vector expectedNormal1 = new Vector(0, 0, 1);
        Vector expectedNormal2 = new Vector(0, 0, -1);
        assertEquals(expectedNormal1, triangle.getNormal(pointOnSurface),
                "Triangle's normal is not correct");
        assertEquals(expectedNormal2, triangle.getNormal(pointOnSurface),
                "Triangle's normal is not correct");
    }
    @Test
    void testFindintersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the triangle (0 points)
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Point p01 = new Point(-1, -1, 0);
        Vector v110 = new Vector(1, 1, 0);
        assertNull(triangle.findIntersections(new Ray(p01, v110)), "Ray's line out of triangle");
    }

    @Test
    void testFindIntersection(){
        // ============ Equivalence Partitions Tests ==============
        //the tests will only check cases that are different from the plane (which means the tests cases are on the same plane)
        // TC01: Ray's line is inside the triangle
        Triangle triangle = new Triangle(new Point(-2, 0, 0), new Point(2, 0, 0), new Point(0, 2, 0));
        Point p01 = new Point(0, 1, 0);
        Vector v01 = new Vector(0, 0.5, 0);
        assertNull(triangle.findIntersections(new Ray(p01, v01)), "Ray's line out of triangle");

        // TC02: Ray intersects the edge of the triangle
        Point p02 = new Point(2, 2, 0);
        Vector v02 = new Vector(-1, -1, 0);
        Point expectedP1 = new Point(1, 1, 0);
        assertEquals(expectedP1,triangle.findIntersections(new Ray(p02, v02)), "Ray's line crosses triangle");

        // TC03: Ray intersects the vertex of the triangle
        Point p03 = new Point(0, 3, 0);
        Vector v03 = new Vector(0, -2, 0);
        Point expectedP2 = new Point(0, 2, 0);
        assertEquals(expectedP2,triangle.findIntersections(new Ray(p03, v03)), "Ray's line crosses triangle");

        //============= Boundary Values Tests ==================
        // TC10: Ray starts at the edge of the triangle
        Point p10 = new Point(1, 1, 0);
        Vector v10 = new Vector(1, 1, 0);
        assertNull(triangle.findIntersections(new Ray(p10, v10)), "Ray's line crosses triangle");

        // TC11: Ray starts at the vertex of the triangle
        Point p11 = new Point(0, 2, 0);
        Vector v11 = new Vector(1, 1, 0);
        assertNull(triangle.findIntersections(new Ray(p11, v11)), "Ray's line crosses triangle");

        // TC12: Ray starts at the continuation of the edge triangle and goes inside
        Point p12 = new Point(3, 0, 0);
        Vector v12 = new Vector(-1, 0, 0);
        assertNull(triangle.findIntersections(new Ray(p12, v12)), "Ray's line crosses triangle");
    }

}