package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.isZero;
import geometries.Intersectable.Intersection;

/**
 * class to represent a ray in the space
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor to initialize ray based on head point and direction vector
     * @param p head point
     * @param v direction vector
     */
    public Ray(Point p, Vector v) {
        head = p;
        direction = v.normalize();
    }

    /**
     * method to compare two rays in space (both fields and class type)
     * @param obj an unknown object
     * @return if obj is equal to this ray
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
     * method to convert ray to string
     * @return ray in string type
     */
    @Override
    public String toString() {
        return "head = " + head.toString()
                + "direction = " + direction.toString();

    }
    /**
     * method to get the head point of the ray
     * @return the head point of the ray
     */
    public Point getHead() {
        return head;
    }
    /**
     * method to get the direction of the ray
     * @return the direction of the ray
     */
    public Vector getDirection() {
        return direction;
    }
    /**
     * method to get a point on the ray based on t
     * @param t the distance from the head point
     * @return the point on the ray
     */
    public Point getPoint(double t) {
        if(isZero(t)) {
            return head;
        }
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point to the ray from a list of points.
     * This method is a placeholder and should be implemented in subclasses.
     *
     * @param points the list of points to search
     * @return the closest point to the ray, currently returns null since it's not implemented
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null ? null
                : findClosestIntersection(points.stream()
                .map(p -> new Intersection(null, p,new Material()))
                .toList()).point;
    }


    public Intersection findClosestIntersection(List<Intersection> intersections) {
        if (intersections == null)  {
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