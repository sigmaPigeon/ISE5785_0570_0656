package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
/*
    * Unit tests for {@link Ray#getPoint(double)}.
 */
    @Test
    void testGetPoint() {
        //============== Equivalence Partitions Tests ==============
        // TC01: Test the getPoint method with a positive distance
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(5, 0, 0));
        double distance = 2.0;
        Point expectedPoint = new Point(3, 1, 1);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for positive distance");

        // TC02: Test the getPoint method with a negative distance
        distance = -2.0;
        expectedPoint = new Point(-1, 1, 1);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for negative distance");

        //============== Boundary Values Tests ==================
        // TC10: Test the getPoint method with a distance of 0
        distance = 0.0;
        expectedPoint = new Point(1, 1, 1);
        assertEquals(expectedPoint, ray.getPoint(distance), "getPoint method failed for distance of 0");
    }

    void testFindClosestPoint() {
        //============== Equivalence Partitions Tests ==============
        // TC01: Test the findClosestPoint method with a point on the ray
        Ray ray = new Ray(new Point(1, 1, 1), new Vector(5, 0, 0));
        List<Point> pointOnRay = List.of(new Point(4, 1, 1), new Point(3, 1, 1), new Point(5, 1, 1));
        assertEquals(pointOnRay, ray.findClosestPoint(pointOnRay),
                "findClosestPoint failed for point on ray");

        //============== Boundary Values Tests ==================
        // TC10: Test the findClosestPoint method with an empty list
        List<Point> emptyList = List.of();
        assertNull(ray.findClosestPoint(emptyList),
                "findClosestPoint should return null for an empty list");

        // TC11: Test the findClosestPoint method with the first point being the closest
        List<Point> pointsWithClosestFirst = List.of(new Point(2, 1, 1), new Point(3, 1, 1), new Point(4, 1, 1));
        assertEquals(new Point(2, 1, 1), ray.findClosestPoint(pointsWithClosestFirst),
                "findClosestPoint failed for closest point being the first in the list");

        // TC12: Test the findClosestPoint method with the last point being the closest
        List<Point> pointsWithClosestLast = List.of(new Point(4, 1, 1), new Point(3, 1, 1), new Point(2, 1, 1));
        assertEquals(new Point(2, 1, 1), ray.findClosestPoint(pointsWithClosestLast),
                "findClosestPoint failed for closest point being the last in the list");
    }
}