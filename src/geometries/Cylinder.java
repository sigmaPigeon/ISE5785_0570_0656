package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        if (t == 0) {
            return v.normalize();
        }
        // If the point is not on the axis, find the projection of the point onto the axis
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return point.subtract(o).normalize();
    }
}
