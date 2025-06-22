package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.isZero;
import geometries.Intersectable.Intersection;

/**
 * The Ray class represents a ray in 3D space, defined by a starting point (head) and a direction vector.
 * Rays are used for geometric calculations such as intersections with objects.
 */
public class Ray {
    /**
     * The starting point (origin) of the ray.
     */
    protected final Point head;
    /**
     * The normalized direction vector of the ray.
     */
    protected final Vector direction;

    /**
     * Constructs a Ray with the specified head point and direction vector.
     * The direction vector is normalized.
     *
     * @param p the head (origin) point of the ray
     * @param v the direction vector of the ray
     */
    public Ray(Point p, Vector v) {
        head = p;
        direction = v.normalize();
    }

    /**
     * Compares this ray to another object for equality.
     * Two rays are equal if their head points and direction vectors are equal.
     *
     * @param obj the object to compare with
     * @return true if the rays are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ray tmp)) {
            return false;
        }
        return head.equals(tmp.head)
                && direction.equals(tmp.direction);
    }

    /**
     * Returns a string representation of the ray.
     *
     * @return a string describing the ray
     */
    @Override
    public String toString() {
        return "head = " + head.toString()
                + "direction = " + direction.toString();

    }

    /**
     * Returns the head (origin) point of the ray.
     *
     * @return the head point
     */
    public Point getHead() {
        return head;
    }

    /**
     * Returns the normalized direction vector of the ray.
     *
     * @return the direction vector
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Returns a point on the ray at a given distance t from the head.
     *
     * @param t the distance from the head point
     * @return the point on the ray at distance t
     */
    public Point getPoint(double t) {
        if(isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point to the ray from a list of points.
     * Returns null if the list is null or empty.
     *
     * @param points the list of points to search
     * @return the closest point to the ray, or null if not found
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null ? null
                : findClosestIntersection(points.stream().map(p -> new Intersection(null, p)).toList()).point;
    }

    /**
     * Finds the closest intersection to the ray from a list of intersections.
     * Returns null if the list is null or empty.
     *
     * @param intersections the list of intersections to search
     * @return the closest intersection to the ray, or null if not found
     */
    public Intersection findClosestIntersection(List<Intersection> intersections) {
        if (intersections == null || intersections.isEmpty()) {
            return null; // No intersections to compare
        }
        Intersection closestIntersection = null;
        double minDistance = Double.MAX_VALUE;

        for (Intersection intersection : intersections) {
            double distance = head.distance(intersection.getPoint());
            if (distance < minDistance) {
                minDistance = distance;
                closestIntersection = intersection;
            }
        }
        return closestIntersection;
    }
}