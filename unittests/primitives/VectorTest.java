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
class VectorTest {
    @Test
    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     * <p>
     * This method checks the correctness of the constructor for a vector.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Constructor with valid values</li>
     *     <li>Constructor with zero vector (should throw an exception)</li>
     * </ul>
     */
    void constructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the constructor of a vector
        Vector v1 = new Vector(1, 2, 3);
        assertNotNull(v1, "Vector constructor failed");

        // TC02: Test the constructor of a zero vector
        assertThrows(IllegalArgumentException.class, () -> {
            Vector zeroVector = new Vector(0, 0, 0);
        }, "Zero vector should throw an exception");
    }
    @Test
    /**
     * Test method for {@link Vector#add(Vector)}.
     * <p>
     * This method checks the correctness of the addition of two vectors.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Addition of two vectors</li>
     * </ul>
     */
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the addition of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector expected = new Vector(5, 7, 9);
        // The expected value is the sum of the two vectors
        assertEquals(expected, v1.add(v2), "Adding two vectors failed");
    }

    @Test
    /**
     * Test method for {@link Vector#subtract(Vector)}.
     * <p>
     * This method checks the correctness of the subtraction of two vectors.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Subtraction of two vectors</li>
     * </ul>
     */
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the subtraction of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector expected = new Vector(-3, -3, -3);
        // The expected value is the difference of the two vectors
        assertEquals(expected, v1.subtract(v2), "Subtracting two vectors failed");
    }

    @Test
    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     * <p>
     * This method checks the correctness of the dot product of two vectors.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Dot product of two vectors</li>
     * </ul>
     */
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the dot product of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        double expected = 32.0;
        // The expected value is the dot product of the two vectors
        assertEquals(expected, v1.dotProduct(v2), "Dot product of two vectors failed");
    }
/**
     * Test method for {@link Vector#crossProduct(Vector)}.
     * <p>
     * This method checks the correctness of the cross product of two vectors.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Cross product of two vectors</li>
     * </ul>
     */
    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the cross product of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector expected = new Vector(-3, 6, -3);
        // The expected value is the cross product of the two vectors
        assertEquals(expected, v1.crossProduct(v2), "Cross product of two vectors failed");

        // TC02: Test the cross product of two vectors with zero vector
        assertThrows(IllegalArgumentException.class, () -> {
            Vector parallelV1 = new Vector(1, 2, 3);
            Vector parallelV2 = new Vector(2, 4, 6);
            parallelV1.crossProduct(parallelV2);
        }, "Cross product of parallel vectors should throw an exception");
    }
/**
     * Test method for {@link Vector#lengthSquared()}.
     * <p>
     * This method checks the correctness of the length squared of a vector.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Length squared of a vector</li>
     * </ul>
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the length squared of a vector
        Vector v1 = new Vector(1, 2, 3);
        double expected = 14.0;
        // The expected value is the square of the length of the vector
        assertEquals(expected, v1.lengthSquared(), "Length squared of a vector failed");
        }
/**
     * Test method for {@link Vector#length()}.
     * <p>
     * This method checks the correctness of the length of a vector.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Length of a vector</li>
     * </ul>
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the length of a vector
        Vector v1 = new Vector(1, 2, 3);
        double expected = Math.sqrt(14.0);
        // The expected value is the length of the vector
        assertEquals(expected, v1.length(), "Length of a vector failed");
    }
/**
     * Test method for {@link Vector#normalize()}.
     * <p>
     * This method checks the correctness of the normalization of a vector.
     * <p>
     * The test cases cover:
     * <ul>
     *     <li>Normalization of a vector</li>
     * </ul>
     */
    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the normalization of a vector
        Vector v1 = new Vector(1, 2, 3);
        Vector normalized = v1.normalize();
        // The expected value is the normalized vector
        assertEquals(1.0, normalized.length(), "Normalized vector is not a unit vector");
        // The expected value is the original vector scaled by 1/length
        assertNotEquals(v1, normalized, "Normalization should return a different vector");
    }
}