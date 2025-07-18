package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link Cylinder#getNormal(Point)}.
 * This class contains tests for the normal vector calculation of a finite cylinder.
 * <p>
 * The tests cover:
 * <ul>
 *     <li>Normals at points on the curved surface</li>
 *     <li>Normals at points on the first and second bases (caps)</li>
 *     <li>Normals at boundary values, such as points on the axis and at the tips of the bases</li>
 * </ul>
 */
class CylinderTest {
    /**
     * Test method for {@link Cylinder#getNormal(Point)}.
     * <p>
     * This method checks the correctness of the normal vector returned by the cylinder
     * for various points:
     * <ul>
     *     <li>On the curved surface</li>
     *     <li>On the first base (cap)</li>
     *     <li>On the second base (cap)</li>
     *     <li>On the axis at both bases</li>
     *     <li>At the tips of both bases</li>
     * </ul>
     * <p>
     * Each test case asserts that the returned normal vector matches the expected value.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a cylinder at a point on its surface
        Cylinder cylinder = new Cylinder(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0)), 1, 2);
        Point pointOnSurface = new Point(2, 1, 0);
        Vector expectedNormal = new Vector(0, 1, 0);
        assertEquals(expectedNormal, cylinder.getNormal(pointOnSurface),
                "Cylinder's normal is not correct");

        // TC02: Test the normal of a cylinder at a point on its first base (cap)
        Point pointOnBase1 = new Point(1, 0.5, 0);
        Vector expectedNormalOnBase1 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnBase1, cylinder.getNormal(pointOnBase1),
                "Cylinder's normal at a point on its first base is not correct");

        // TC03: Test the normal of a cylinder at a point on its second base (cap)
        Point pointOnBase2 = new Point(3, 0.5, 0);
        Vector expectedNormalOnBase2 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnBase2, cylinder.getNormal(pointOnBase2),
                "Cylinder's normal at a point on its second base is not correct");

        // ============= Boundary Values Tests ==================
        // TC10: Test the normal of a cylinder at a point on the first base and the axis
        // The normal at a point on the first base should be perpendicular to the axis
        Point pointOnAxis1 = new Point(1, 0, 0);
        Vector expectedNormalOnAxis1 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnAxis1, cylinder.getNormal(pointOnAxis1),
                "Cylinder's normal at a point on its first base and the axis is not correct");

        // TC11: Test the normal of a cylinder at a point on the second base and the axis
        // The normal at a point on the second base should be perpendicular to the axis
        Point pointOnAxis2 = new Point(3, 0, 0);
        Vector expectedNormalOnAxis2 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnAxis2, cylinder.getNormal(pointOnAxis2),
                "Cylinder's normal at a point on its second base and the axis is not correct");

        // TC12: Test the normal of a cylinder at a point on the first base and the tip
        // The normal at a point on the first base should be perpendicular to the axis
        Point pointOnTip1 = new Point(1, 1, 0);
        Vector expectedNormalOnTip1 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnTip1, cylinder.getNormal(pointOnTip1),
                "Cylinder's normal at a point on its first base and the tip is not correct");

        // TC13: Test the normal of a cylinder at a point on the second base and the tip
        // The normal at a point on the second base should be perpendicular to the axis
        Point pointOnTip2 = new Point(3, 1, 0);
        Vector expectedNormalOnTip2 = new Vector(1, 0, 0);
        assertEquals(expectedNormalOnTip2, cylinder.getNormal(pointOnTip2),
                "Cylinder's normal at a point on its second base and the tip is not correct");
    }


    @Test
    void testCylinderIntersections() {
        // Cylinder along Z axis, radius 1, height 2, offset from origin
        Cylinder cylinder = new Cylinder(new Ray(new Point(2, 2, 2), new Vector(0, 0, 1)), 1, 2);

        // TC01: Ray intersects the curved surface at two points (not parallel or perpendicular to axis)
        Ray ray1 = new Ray(new Point(4, 3, 3), new Vector(-1, -0.5, 0.5));
        List<Intersectable.Intersection> intersections1 = cylinder.calculateIntersectionsHelper(ray1);
        assertNotNull(intersections1, "Expected intersections");
        assertTrue(intersections1.size() >= 1, "Cylinder: Should have at least 1 intersection");

        // TC02: Ray intersects the top cap (not through the center)
        Ray ray2 = new Ray(new Point(2.5, 2, 5), new Vector(0, 0.5, -1));
        List<Intersectable.Intersection> intersections2 = cylinder.calculateIntersectionsHelper(ray2);
        assertNotNull(intersections2, "Expected intersection with cap");
        assertTrue(intersections2.size() >= 1, "Cylinder: Should have at least 1 intersection with cap");

        // TC03: Ray misses the cylinder completely
        Ray ray3 = new Ray(new Point(10, 10, 10), new Vector(1, 1, 1));
        List<Intersectable.Intersection> intersections3 = cylinder.calculateIntersectionsHelper(ray3);
        assertNull(intersections3, "Expected no intersection");
    }


}
