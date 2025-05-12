package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface to represent geometries that can be intersected by rays.
 * This interface defines a method to find the intersection points of a ray with the geometry.
 */
public interface Intersectable {
    /**
     * Method to find the intersection points of a ray with the geometry
     * @param ray the ray to intersect with
     * @return a list of intersection points
     */
    List<Point> findIntersections(Ray ray);
}
