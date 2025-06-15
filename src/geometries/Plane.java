package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class to represent a plane in 3D space.
 * A plane is defined by a point on the plane and a normal vector perpendicular to the plane.
 */
public class Plane extends Geometry {
    private final Point q; // A point on the plane
    private final Vector normal; // The normal vector of the plane

    /**
     * Constructor to initialize a plane based on three points.
     * The three points must not be collinear.
     *
     * @param p1 First point on the plane
     * @param p2 Second point on the plane
     * @param p3 Third point on the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        // Create a plane using 3 points
        normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
        q = p1;
    }

    /**
     * Constructor to initialize a plane based on a point and a normal vector.
     *
     * @param q     A point on the plane
     * @param normal The normal vector of the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }
    public Vector getNormal(){
        return normal;
    }
    /**
     * Method to get the normal vector of the plane.
     * The normal vector is constant for the entire plane.
     *
     * @param p1 A point on the plane (not used in this implementation)
     * @return The normal vector of the plane
     */
    @Override
    public Vector getNormal(Point p1) {
        // If the normal is already defined, return it
        if (normal != null) {
            return normal;
        }
        Vector v1 = p1.subtract(q);
        Vector v2 = p1.subtract(q);
        return v1.crossProduct(v2).normalize();
    }

    /**
     * Method to find the intersection points of a ray with the plane.
     *
     * @param ray The ray to intersect with the plane
     * @return A list containing the intersection point, or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if(ray.getHead().equals(q)) return null;
        // Check if the ray intersects with the plane
        Vector n = normal;
        Vector v = ray.getDirection();
        double nv = n.dotProduct(v);
        if (isZero(nv)) {
            return null; // The ray is parallel to the plane
        }
        // Calculate the intersection point
        double d = n.dotProduct(q.subtract(ray.getHead()));
        if(isZero(d)) {
            return null; // The ray is on the plane
        }
        double t1 = alignZero(d / nv);
        if (t1 < 0) {
            return null; // The intersection point is behind the ray's head
        }
        Point intersectionPoint = ray.getPoint(t1);
        return List.of(new Intersection(this,intersectionPoint,material));
    }
}