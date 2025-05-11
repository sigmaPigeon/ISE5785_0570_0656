package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a cylinder at a point on its surface
        Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1, 2);
        Point pointOnSurface = new Point(1, 1, 0);
        Vector expectedNormal = new Vector(1, 0, 0);
        assertEquals(expectedNormal, cylinder.getNormal(pointOnSurface),
                "Cylinder's normal is not correct");
        //TC02: Test the normal of a cylinder at a point on its first base
        Point pointOnBase1 = new Point(0.5, 0, 0);
        Vector expectedNormalOnBase1 = new Vector(0, 0, -1);
        assertEquals(expectedNormalOnBase1, cylinder.getNormal(pointOnBase1),
                "Cylinder's normal at a point on its first base is not correct");

        //TC03: Test the normal of a cylinder at a point on its second base
        Point pointOnBase2 = new Point(0.5, 0, 2);
        Vector expectedNormalOnBase2 = new Vector(0, 0, 1);
        assertEquals(expectedNormalOnBase2, cylinder.getNormal(pointOnBase2),
                "Cylinder's normal at a point on its second base is not correct");

        //============= Boundary Values Tests ==================
        // TC10: Test the normal of a cylinder at a point on the first base and the axis
        // The normal at a point on the first base should be perpendicular to the axis
        Point pointOnAxis1 = new Point(0, 0, 0);
        Vector expectedNormalOnAxis1 = new Vector(0, 0, -1);
        assertEquals(expectedNormalOnAxis1, cylinder.getNormal(pointOnAxis1),
                "Cylinder's normal at a point on its first base and the axis is not correct");

        // TC11: Test the normal of a cylinder at a point on the second base and the axis
        // The normal at a point on the second base should be perpendicular to the axis
        Point pointOnAxis2 = new Point(0, 0, 2);
        Vector expectedNormalOnAxis2 = new Vector(0, 0, 1);
        assertEquals(expectedNormalOnAxis2, cylinder.getNormal(pointOnAxis2),
                "Cylinder's normal at a point on its second base and the axis is not correct");

        // TC12: Test the normal of a cylinder at a point on the first base and the tip
        // The normal at a point on the first base should be perpendicular to the axis
        Point pointOnTip1 = new Point(0, 0, 1);
        Vector expectedNormalOnTip1 = new Vector(0, 0, -1);
        assertEquals(expectedNormalOnTip1, cylinder.getNormal(pointOnTip1),
                "Cylinder's normal at a point on its first base and the tip is not correct");

        // TC13: Test the normal of a cylinder at a point on the second base and the tip
        // The normal at a point on the second base should be perpendicular to the axis
        Point pointOnTip2 = new Point(0, 0, 2);
        Vector expectedNormalOnTip2 = new Vector(0, 0, 1);
        assertEquals(expectedNormalOnTip2, cylinder.getNormal(pointOnTip2),
                "Cylinder's normal at a point on its second base and the tip is not correct");
    }

}