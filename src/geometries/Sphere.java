package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to represent a sphere in the 3D space
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * constructor to initialize a sphere based on a central point and a radius
     * @param point center point
     * @param radius radius
     */
    public Sphere(Point point,double radius){
        super(radius);
        this.center = point;
    }

    /**
     * method to get the normal vector of a point on the circumference
     * @param point the point we want the normal to start from
     * @return currently null
     */
    @Override
    public Vector getNormal(Point point){
        return null;
    }
}
