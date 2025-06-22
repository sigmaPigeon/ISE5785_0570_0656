package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * class to represent a sphere in the 3D space
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor to initialize a sphere based on a central point and a radius
     * @param point center point
     * @param radius radius
     */
    public Sphere(Point point,double radius){
        super(radius);
        this.center = point;
    }

    /**
     * method to get the normal vector of a point on the circumference
     * @param point the point we want the normal to start from
     * @return currently null
     */
    @Override
    public Vector getNormal(Point point){
        // The normal vector of a sphere at a point is the vector from the center to that point
        return point.subtract(center).normalize();
    }

    /**
     * @param ray the ray to intersect with
     * @return a list of intersections with the sphere, or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        Vector L = center.subtract(ray.getHead());
        double tca = ray.getDirection().dotProduct(L);
        double d2 = L.lengthSquared() - tca * tca;
        double r2 = radius * radius;

        if (d2 > r2) {
            return null; // No intersection
        }

        double thc = Math.sqrt(r2 - d2);
        double t1 = tca - thc;
        double t2 = tca + thc;

        List<Intersection> intersections = new java.util.ArrayList<>();
        if (t1 > 0) {
            intersections.add(new Intersection(this, ray.getPoint(t1), material));
        }
        if (t2 > 0 && t2 != t1) {
            intersections.add(new Intersection(this, ray.getPoint(t2), material));
        }

        return intersections.isEmpty() ? null : intersections;
    }
}
