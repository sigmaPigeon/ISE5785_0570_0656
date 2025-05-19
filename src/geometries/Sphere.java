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
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        if (ray.getHead().equals(center)) {
            // The ray starts at the center of the sphere
            return List.of(ray.getPoint(radius));
        }
        Vector u = center.subtract(ray.getHead());
        if(u.normalize().equals(ray.getDirection())){
            //check if the ray is inside the sphere and it directs to the center
            double d =  ray.getHead().distance(center);
            if(d <= radius)
                return List.of(ray.getPoint(d+radius));
            return List.of(ray.getPoint(d-radius),ray.getPoint(d+radius));
        }
        //according to the algorithm learned in class
        double tm=ray.getDirection().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        double th = Math.sqrt(radius * radius - d * d);
        if (d >= radius) {
            return null; // no intersection
        }
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 > 0 && t2 > 0) {
            return List.of(ray.getPoint(t1),
                    ray.getPoint(t2));
        } else if (t1 > 0) {
            return List.of(ray.getPoint(t1));
        } else if (t2 > 0) {
            return List.of(ray.getPoint(t2));
        } else {
            return null; // no intersection
        }
    }
}
