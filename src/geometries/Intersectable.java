package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface to represent geometries that can be intersected by rays.
 * This interface defines a method to find the intersection points of a ray with the geometry.
 */
public abstract class Intersectable {
    /**
     * Method to find the intersection points of a ray with the geometry
     * @param ray the ray to intersect with
     * @return a list of intersection points
     */
    public static class Intersection {
        public final Geometry geometry;
        public final Point point;

        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Intersection)) return false;
            Intersection that = (Intersection) o;
            return  point.equals(that.point);
        }
        @Override
        public String toString() {
            return "Intersection{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    public final List<Point> findIntersections(Ray ray) {
        var list = calculateIntersections(ray);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }
    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray) ;

    public final List<Intersection> calculateIntersections(Ray ray) {
        return  calculateIntersectionsHelper(ray);
    }
}