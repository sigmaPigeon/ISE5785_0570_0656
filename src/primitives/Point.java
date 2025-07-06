package primitives;

/**
 * The Point class represents a point in a 3D space.
 * It provides methods to perform various operations such as addition, subtraction, and distance calculation.
 */
public class Point {

    /**
     * A constant representing the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * The coordinates of the point.
     */
    protected final Double3 xyz;



    /**
     * Constructs a Point with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructs a Point with the specified Double3 object.
     *
     * @param xyz the Double3 object containing the coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Checks if this point is equal to another object.
     * Two points are considered equal if their coordinates are the same.
     *
     * @param obj the object to compare with this point
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point point)) return false;
        return this.xyz.equals(point.xyz);
    }

    /**
     * Returns a string representation of the point.
     * The string representation consists of the coordinates of the point.
     *
     * @return a string representation of the point
     */
    @Override
    public String toString() {
        return "" + xyz;
    }

    /**
     * Adds a vector to this point and returns the resulting point.
     * The addition is performed by adding the corresponding coordinates of the point and the vector.
     *
     * @param vec the vector to add to this point
     * @return the resulting point after addition
     */
    public Point add(Vector vec) {
        return new Point(xyz.add(vec.xyz));
    }

    /**
     * Subtracts another point from this point and returns the resulting vector.
     * The subtraction is performed by subtracting the corresponding coordinates of the other point from this point.
     *
     * @param point the point to subtract from this point
     * @return the resulting vector after subtraction
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     * The squared distance is the sum of the squares of the differences in the corresponding coordinates.
     *
     * @param point the other point to calculate the squared distance to
     * @return the squared distance between this point and the other point
     */
    public double distanceSquared(Point point) {
        double diffX = xyz.d1() - point.xyz.d1();
        double diffY = xyz.d2() - point.xyz.d2();
        double diffZ = xyz.d3() - point.xyz.d3();
        return diffX * diffX + diffY * diffY + diffZ * diffZ;
    }

    /**
     * Calculates the distance between this point and another point.
     * The distance is the square root of the squared distance.
     *
     * @param point the other point to calculate the distance to
     * @return the distance between this point and the other point
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    public Double3 getXyz() {
        return xyz;
    }
    public double getX() {
        return xyz.d1();
    }
    public double getY() {
        return xyz.d2();
    }
    public double getZ() {
        return xyz.d3();
    }
}