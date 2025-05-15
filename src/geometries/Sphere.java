package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector u = center.subtract(ray.getHead());
        double tm=ray.getDirection().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        double th = Math.sqrt(radius * radius - d * d);
        if (d >= radius) {
            return null; // no intersection
        }
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)),
                    ray.getHead().add(ray.getDirection().scale(t2)));
        } else if (t1 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t1)));
        } else if (t2 > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t2)));
        } else {
            return null; // no intersection
        }
    }
}
