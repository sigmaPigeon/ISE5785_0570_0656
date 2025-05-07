package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test

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
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the dot product of two vectors
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        double expected = 32.0;
        // The expected value is the dot product of the two vectors
        assertEquals(expected, v1.dotProduct(v2), "Dot product of two vectors failed");
    }

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

    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the length squared of a vector
        Vector v1 = new Vector(1, 2, 3);
        double expected = 14.0;
        // The expected value is the square of the length of the vector
        assertEquals(expected, v1.lengthSquared(), "Length squared of a vector failed");
        }

    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the length of a vector
        Vector v1 = new Vector(1, 2, 3);
        double expected = Math.sqrt(14.0);
        // The expected value is the length of the vector
        assertEquals(expected, v1.length(), "Length of a vector failed");
    }

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