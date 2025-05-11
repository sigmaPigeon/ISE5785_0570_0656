package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PointTest {
    private static final double DELTA = 0.000001;

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the addition of two points
        Point p1 = new Point(1, 1, 1);
        Vector v1 = new Vector(2, 2, 2);
        Point expected = new Point(3, 3, 3);
        // The expected value is the sum of the point and the vector
        assertEquals(expected, p1.add(v1),
                "Adding a vector to a point should return a new point");
    }
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test the subtraction of two points
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        Vector expected = new Vector(-1, -1, -1);
        // The expected value is a vector from p2 to p1
        assertEquals(expected, p1.subtract(p2),
                "Subtracting two points should return a vector");
    }

    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the distance squared between two points
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        int expected = 3;
        // The expected value is the square of the distance
        assertEquals(expected, p1.distanceSquared(p2),
                "Distance squared between two points is incorrect");
    }

    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the distance between two points
        Point p1 = new Point(1, 1, 1);
        Point p2 = new Point(2, 2, 2);
        double expected = Math.sqrt(3);
        // The expected value is the square root of the distance squared
        assertEquals(expected, p1.distance(p2), DELTA,
                "Distance between two points is incorrect");
    }
}