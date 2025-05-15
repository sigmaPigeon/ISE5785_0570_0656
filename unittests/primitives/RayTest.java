package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testGetPoint() {
        //============== Equivalence Partitions Tests ==============
        // TC01: Test the getPoint method with a positive distance
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(2, 2, 2));
        double distance = 2.0;
        Point expectedPoint = new Point(5, 5, 5);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for positive distance");

        // TC02: Test the getPoint method with a negative distance
        distance = -2.0;
        expectedPoint = new Point(-3, -3, -3);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for negative distance");

        //============== Boundary Values Tests ==================
        // TC10: Test the getPoint method with a distance of 0
        distance = 0.0;
        expectedPoint = new Point(1, 1, 1);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for distance of 0");
    }
}