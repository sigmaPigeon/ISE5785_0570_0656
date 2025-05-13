package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for {@link Vector} class.
 * <p>
 * This class contains tests for the following methods:
 * <ul>
 *     <li>Constructor</li>
 *     <li>Addition</li>
 *     <li>Subtraction</li>
 *     <li>Dot product</li>
 *     <li>Cross product</li>
 *     <li>Length squared</li>
 *     <li>Length</li>
 *     <li>Normalization</li>
 * </ul>
 */
class PointTest {
    /**
     * This constant is used to define the precision for floating-point comparisons.
     * It is set to a small value to account for potential rounding errors in calculations.
     */
    private static final double DELTA = 0.000001;
    /**
     * Test method for {@link Point#Point(double, double, double)}.
     * <p>
     * This method checks the correctness of the constructor for a point.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Constructor with valid values</li>
     * </ul>
     */
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
    /**
     * Test method for {@link Point#subtract(Point)}.
     * <p>
     * This method checks the correctness of the subtraction of two points.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Subtraction of two points</li>
     * </ul>
     */
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
/**
     * Test method for {@link Point#distanceSquared(Point)}.
     * <p>
     * This method checks the correctness of the distance squared between two points.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Distance squared between two points</li>
     * </ul>
     */
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
/**
     * Test method for {@link Point#distance(Point)}.
     * <p>
     * This method checks the correctness of the distance between two points.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Distance between two points</li>
     * </ul>
     */
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