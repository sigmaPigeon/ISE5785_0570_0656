package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class to represent a tube as a geometry object
 */
public class Tube extends RadialGeometry{
    protected final Ray axis;

    /**
     * constructor to initialize a tube based on a ray and radius
     * @param axis the direction of the tube
     * @param radius the width of the tube
     */
    public Tube(Ray axis, double radius){
        super(radius);
        this.axis = axis;
    }

    /**
     * method to get the normal from a certain point
     * @param p the point we want the normal from
     * @return the normal vector based on the tube and the point
     */
    @Override
    public Vector getNormal(Point p) {
        // The normal vector of a tube at a point is the vector from the axis to that point
        Vector v = p.subtract(axis.getHead());
        double t = axis.getDirection().dotProduct(v);
        // If the point is on the axis, return the normalized vector
        if (t == 0) {
            return v.normalize();
        }
        // If the point is not on the axis, find the projection of the point onto the axis
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return p.subtract(o).normalize();
    }

    /*     * method to calculate the intersections of a ray with the tube
     * @param ray the ray to intersect with
     * @return list of intersection points or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // Ray: P = P0 + t*v
        // Tube: |(P - Pa) - ((P - Pa) . va) * va|^2 = r^2
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();
        Point pa = axis.getHead();
        Vector va = axis.getDirection();

        if(p0.equals(pa)) {
            // If the ray's head is at the tube's axis head, return null
            return null;
        }
        Vector deltaP = p0.subtract(pa);
        double vVa = v.dotProduct(va);
        if (isZero(vVa)) {
            // If the ray is parallel to the tube's axis, return null
            return null;
        }
        Vector vMinusVa = v.subtract(va.scale(vVa));
        double a = vMinusVa.lengthSquared();

        double deltaPVa = deltaP.dotProduct(va);
        if (isZero(deltaPVa)) {
            // If the ray is parallel to the tube's axis, return null
            return null;
        }
        Vector deltaPMinusVa = deltaP.subtract(va.scale(deltaPVa));
        double b = 2 * vMinusVa.dotProduct(deltaPMinusVa);

        double c = deltaPMinusVa.lengthSquared() - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) return null;

        double sqrtD = Math.sqrt(discriminant);
        double t1 = (-b + sqrtD) / (2 * a);
        double t2 = (-b - sqrtD) / (2 * a);

        List<Intersection> result = new java.util.ArrayList<>();
        if (t1 > 0) result.add(new Intersection(this, ray.getPoint(t1)));
        if (t2 > 0 && !isZero(t2 - t1)) result.add(new Intersection(this, ray.getPoint(t2)));
        return result.isEmpty() ? null : result;
    }
    @Override
    public void computeBoundingBox() {
        // Tube is infinite; no finite bounding box.
        box = null;
    }
}
