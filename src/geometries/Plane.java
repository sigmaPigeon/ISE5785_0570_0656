package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to represent a plane in the space
 */
public class Plane extends Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * constructor to initialize a plane based on 3 points
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1,Point p2,Point p3){
        normal = null;
        q = p1;
    }

    /**
     * Constructor to initialize a plane based on a point and a vector
     * @param q point
     * @param normal vector
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * method to get a normal vector of a plane based on a point
     * @param unused
     * @return the normal vector from the point
     */

    @Override
    public Vector getNormal(Point unused) {
        return null;
    }
}
