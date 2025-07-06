package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class to represent a finite tube
 */
public class Cylinder extends Tube{
    private final double height;
    /**
     * constructor to initialize a cylinder based on a ray ,radius and height
     * @param axis the direction of the cylinder
     * @param radius the width of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axis, double radius, double height){
        super(axis,radius);
        this.height = height;
    }

    /**
     * method to get the normal from a certain point
     * @param point the point we want the normal from
     * @return the normal vector based on the cylinder and the point
     */
    @Override
    public Vector getNormal(Point point){
        //if the point is on the top or bottom base of the cylinder
        Vector v1 = new Vector(point.getXyz());
        Vector v2 = new Vector(axis.getHead().getXyz());
        if (v1.dotProduct(axis.getDirection()) == v2.dotProduct(axis.getDirection()) ||
                v1.dotProduct(axis.getDirection()) == height + v2.dotProduct(axis.getDirection())) {
            return axis.getDirection();
        }
        // The normal vector of a cylinder at a point is the vector from the axis to that point
        Vector v = point.subtract(axis.getHead());
        double t = axis.getDirection().dotProduct(v);
        // If the point is on the axis, return the normalized vector
        if (isZero(t)) {
            return v.normalize();
        }
        // If the point is not on the axis, find the projection of the point onto the axis
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        if(o.equals(point))
            return axis.getDirection();
        return point.subtract(o).normalize();
    }
    /**
     * method to calculate the intersections of a ray with the cylinder
     * @param ray the ray to intersect with
     * @return list of intersection points or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if (height <= 0)
            throw new IllegalArgumentException("Height must be greater than zero");
        List<Intersection> tubeIntersections = super.calculateIntersectionsHelper(ray);
        List<Intersection> result = new java.util.ArrayList<>();// Filter tube intersections to be within the cylinder's height
        if (tubeIntersections != null) {
            for (Intersection inter : tubeIntersections) {
                if (inter.point.equals(axis.getHead()) || inter.point.equals(axis.getHead().add(axis.getDirection().scale(height)))) {
                    result.add(new Intersection(this, inter.point));
                    continue; // Skip the top and bottom points
                }
                Vector fromBase = inter.point.subtract(axis.getHead());
                double projection = fromBase.dotProduct(axis.getDirection());
                if (projection >= 0 && projection <= height) {
                    result.add(new Intersection(this, inter.point));
                }
            }
        }
        // Intersect with cylinder caps (top and bottom)
        Vector va = axis.getDirection();
        Point base = axis.getHead();
        Point top = base.add(va.scale(height));
        // Bottom cap
        double denom = va.dotProduct(ray.getDirection());
        if (!isZero(denom)) {
            double t = va.dotProduct(base.subtract(ray.getHead())) / denom;
            if (t > 0) {
                if (isZero(t))
                    return null; // The ray starts at the base, no intersection
                Point p = ray.getPoint(t);
                if (p.equals(top) || p.subtract(base).lengthSquared() <= radius * radius)
                    result.add(new Intersection(this, p));
            }
        }
        // Top cap
        denom = va.dotProduct(ray.getDirection());
        if (!isZero(denom)) {
            double t = va.dotProduct(top.subtract(ray.getHead())) / denom;
            if (t > 0) {
                if ( isZero(t))
                    return null; // The ray starts at the top, no intersection
                Point p = ray.getPoint(t);
                if (p.equals(top) || p.subtract(top).lengthSquared() <= radius * radius)
                    result.add(new Intersection(this, p));
            }
        }
        return result.isEmpty() ? null : result;
    }
    @Override
    public void computeBoundingBox() {
        // Compute bounding box for a finite cylinder
        // Assume axisRay is the axis, height is the length, and radius is the radius
        Point p0 = axis.getHead();
        Vector dir = axis.getDirection().normalize();
        Point p1 = p0.add(dir.scale(height));

        double minX = Math.min(p0.getX(), p1.getX()) - radius;
        double maxX = Math.max(p0.getX(), p1.getX()) + radius;
        double minY = Math.min(p0.getY(), p1.getY()) - radius;
        double maxY = Math.max(p0.getY(), p1.getY()) + radius;
        double minZ = Math.min(p0.getZ(), p1.getZ()) - radius;
        double maxZ = Math.max(p0.getZ(), p1.getZ()) + radius;

        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
}