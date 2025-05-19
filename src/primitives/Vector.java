package primitives;

import java.util.Objects;

/**
 * The Vector class represents a vector in a 3D space.
 * It provides methods to perform various vector operations such as addition, scaling, dot product, and cross product.
 */
public class Vector extends Point {

    public static Vector AXIS_X = new Vector(1, 0, 0);
    public static Vector AXIS_Y = new Vector(0, 1, 0);
    public static Vector AXIS_Z = new Vector(0, 0, 1);
    /**
     * Constructs a Vector with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */

    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructs a Vector with the specified Double3 object.
     *
     * @param xyz the Double3 object containing the coordinates
     * @throws IllegalArgumentException if the vector is a zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("ERROR: zero vector");
        }
    }

    /**
     * Checks if this vector is equal to another object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector vec)) return false;
        return Objects.equals(this.xyz, vec.xyz);
    }

    /**
     * Adds another vector to this vector and returns the resulting vector.
     *
     * @param v the vector to add
     * @return the resulting vector
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * Scales this vector by a scalar and returns the resulting vector.
     *
     * @param scalar the scalar to scale by
     * @return the resulting vector
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param v the other vector
     * @return the dot product
     */
    public double dotProduct(Vector v) {
        return xyz.d1() * v.xyz.d1()
                + xyz.d2() * v.xyz.d2()
                + xyz.d3() * v.xyz.d3();
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param v the other vector
     * @return the resulting vector
     * @throws IllegalArgumentException if the vectors are parallel
     */
    public Vector crossProduct(Vector v) {
        double a1 = xyz.d1(), a2 = xyz.d2(), a3 = xyz.d3();
        double b1 = v.xyz.d1(), b2 = v.xyz.d2(), b3 = v.xyz.d3();

        double c1 = a2 * b3 - a3 * b2;
        double c2 = a3 * b1 - a1 * b3;
        double c3 = a1 * b2 - a2 * b1;
        if (Util.isZero(c1 * c1 + c2 * c2 + c3 * c3)) {
            throw new IllegalArgumentException("ERROR: parallel vectors");
        }
        return new Vector(c1, c2, c3);
    }

    /**
     * Calculates the squared length of this vector.
     *
     * @return the squared length
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes this vector and returns the resulting unit vector.
     *
     * @return the resulting unit vector
     */
    public Vector normalize() {
        double length = length();
        return new Vector(xyz.scale(1.0/length));
    }
}