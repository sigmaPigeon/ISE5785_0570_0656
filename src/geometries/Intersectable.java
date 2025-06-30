package geometries;

import lighting.LightSource;
import primitives.*;

import java.util.List;

/**
 * Abstract class to represent geometries that can be intersected by rays.
 * This class defines methods to find intersection points and details of intersections.
 */
public abstract class Intersectable {
    /**
     * Class to represent an intersection between a ray and a geometry.
     * Stores the geometry, intersection point, material, and additional intersection details.
     */
    public static class Intersection {
        /**
         * The geometry that was intersected.
         */
        public final Geometry geometry;
        /**
         * The intersection point.
         */
        public final Point point;
        /**
         * The material at the intersection point.
         */
        public final Material material;
        /**
         * The direction of the incoming ray at the intersection.
         */
        public Vector direction;
        /**
         * The normal vector at the intersection point.
         */
        public Vector normal;
        /**
         * The dot product of direction and normal.
         */
        public Double dxn;
        /**
         * The light source relevant to the intersection.
         */
        public LightSource lightSource;
        /**
         * The direction from the intersection point to the light source.
         */
        public Vector lightDirection;
        /**
         * The dot product of light direction and normal.
         */
        public double ldxn;

        /**
         * Returns the intersection point.
         * @return the intersection point
         */
        public Point getPoint() {
            return point;
        }

        /**
         * Constructs an Intersection with the given geometry, point, and material.
         * @param geometry the intersected geometry
         * @param point the intersection point
         */
        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
            this.material = geometry == null ? null : geometry.getMaterial();
        }

        /**
         * Checks if this intersection is equal to another object.
         * Equality is based on the intersection point.
         * @param o the object to compare
         * @return true if equal, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Intersection)) return false;
            Intersection that = (Intersection) o;
            return  point.equals(that.point);
        }

        /**
         * Returns a string representation of the intersection.
         * @return string representation
         */
        @Override
        public String toString() {
            return "Intersection{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersection points of a ray with the geometry.
     * @param ray the ray to intersect with
     * @return a list of intersection points, or null if none
     */
    public final List<Point> findIntersections(Ray ray) {
        List<Intersection> list = calculateIntersections(ray);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    /**
     * Helper method to calculate intersection details for a ray.
     * Must be implemented by subclasses.
     * @param ray the ray to intersect with
     * @return a list of intersection details
     */
    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray);

    /**
     * Calculates the intersection details of a ray with the geometry.
     * @param ray the ray to intersect with
     * @return a list of intersection details
     */
    public final List<Intersection> calculateIntersections(Ray ray) {
        return  calculateIntersectionsHelper(ray);
    }
}