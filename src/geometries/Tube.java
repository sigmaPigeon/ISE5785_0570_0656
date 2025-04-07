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
        return null;
    }
}
