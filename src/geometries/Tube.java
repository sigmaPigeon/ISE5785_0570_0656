package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}
