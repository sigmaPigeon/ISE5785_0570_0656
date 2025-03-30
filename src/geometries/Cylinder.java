package geometries;

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
        return null;
    }
}
