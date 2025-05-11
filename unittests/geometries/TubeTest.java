package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normal of a tube at a point on its surface
        Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1);
        Point pointOnSurface = new Point(1, 1, 0);
        Vector expectedNormal = new Vector(1, 0, 0);
        assertEquals(expectedNormal, tube.getNormal(pointOnSurface),
                "Tube's normal is not correct");
        //============= Boundary Values Tests ==================
        // TC10: Test the normal of a tube at a point on its axis
        // The normal at a point on the axis should be perpendicular to the axis
        Point pointOnAxis = new Point(0, 0, 0);
        Vector expectedNormalOnAxis = new Vector(0, 1, 0);
        assertEquals(expectedNormalOnAxis, tube.getNormal(pointOnAxis),
                "Tube's normal at a point on its axis is not correct");
    }


}