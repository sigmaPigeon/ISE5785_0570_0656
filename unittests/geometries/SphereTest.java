package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link geometries.Sphere}.
 * This class contains tests for the constructor, normal vector calculation, and intersection
 * of a sphere with a ray.
 * <p>
 * The tests cover:
 * <ul>
 *     <li>Constructor with center and radius</li>
 *     <li>Normal vector calculation</li>
 *     <li>Intersection with a ray</li>
 * </ul>
 */
class SphereTest {
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a sphere at a point on its surface
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        Point pointOnSurface = new Point(1, 0, 0);
        Vector expectedNormal = new Vector(1, 0, 0);
        assertEquals(expectedNormal, sphere.getNormal(pointOnSurface),
                "Sphere's normal is not correct");
    }

    /** A point used in some tests */
    private final Point p001 = new Point(0, 0, 1);
    /** A point used in some tests */
    private final Point p100 = new Point(1, 0, 0);
    /** A vector used in some tests */
    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p0500 = new Point(0.5, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310));
        assertNotNull(result1, "Can't be empty list");
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        final var result2 = sphere.findIntersections(new Ray(p0500, new Vector(0.5,1,0)));
        assertNotNull(result2, "Can't be empty list");
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,1,0)), result2, "Ray starts inside the sphere");
        // TC03: Ray starts inside the sphere (1 points)
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0),v310)), "Ray's line out of sphere");




        // =============== Boundary Values Tests ==================
        // **** Group 1: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        final var result4 = sphere.findIntersections(new Ray(new Point(1,0,1),new Vector(0,1,-1)));
        assertNotNull(result4, "Can't be empty list");
        assertEquals(1, result4.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,1,0)), result4, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,0,1),v001)),"begin of ray does not count");

        // **** Group 2: Ray's line goes through the center
        // TC21: Ray starts before the sphere (2 points)
        final var result5 = sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(-1,0,0)));
        assertNotNull(result5, "Can't be empty list");
        assertEquals(2, result5.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,0),new Point(0,0,0)), result5, "Ray starts at sphere and goes inside");

        // TC22: Ray starts at sphere and goes inside (1 points).
        final var result6 = sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(0,-1,0)));
        assertNotNull(result6, "Can't be empty list");
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,-1,0)), result6, "Ray starts at sphere and goes inside");

        // TC24: Ray starts at the center (1 points)
        final var result7 = sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(0,1,0)));
        assertNotNull(result7, "Can't be empty list");
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,1,0)), result7, "Ray starts at the center");
        // TC25: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,0))), "begin of ray does not count");
        // TC26: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0))), "begin of ray does not count");
        // **** Group 3: Ray's line is tangent to the sphere (all tests 0 points)
        // TC31: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2,-1,0), new Vector(0,1,0))), "begin of ray does not count");
        // TC32: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(0,1,0))), "begin of ray does not count");
        // TC33: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2,1,0), new Vector(0,1,0))), "begin of ray does not count");
        // **** Group 4: Special cases


        // TC42: Ray's starts inside, ray is orthogonal to ray start to sphere's center line
        final var result8 = sphere.findIntersections(new Ray(new Point(1.6,0,0), new Vector(0,0.8,0)));
        assertNotNull(result8, "Can't be empty list");
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1.6,0.8,0)), result8, "Ray starts at sphere and goes inside");


    }



}