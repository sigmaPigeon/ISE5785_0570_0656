package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to represent a plane in the space
 */
public class Plane {
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
     * method to get the normal of a plane
     * @return normal vector
     */
    public Vector getNormal() {return normal;}

    /**
     * method to get a normal vector of a plane based on a point
     * @param p1 the first direction where we want the normal vector to start from
     * @return the normal vector from the point
     */
    public Vector getNormal(Point p1) { return normal;}
}
