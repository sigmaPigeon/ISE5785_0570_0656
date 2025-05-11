package geometries;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        assertEquals(expectedNormal2, plane.getNormal(new Point(0,0 ,0 )), "Plane normal is not correct");
    }

}

