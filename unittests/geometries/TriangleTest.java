package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
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

}