package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

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
}