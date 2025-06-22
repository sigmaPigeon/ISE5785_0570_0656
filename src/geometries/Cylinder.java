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
    public Vector getNormal(Point point) {
        // Cylinder axis
        Ray axisRay = this.axis;
        Vector axisDir = axisRay.getDirection();
        Point axisBase = axisRay.getHead();

        //check if the point equals the axis base
        if (point.equals(axisBase)) {
            return axisDir; // Normal at the base is opposite to the axis direction
        }
        // Project point onto axis to find its position along the axis
        Vector p0_p = point.subtract(axisBase);
        double t = axisDir.dotProduct(p0_p);

        // Check if point is on the bottom cap
        if (t <= 0) {
           // return axisDir.scale(-1).normalize();
            return axisDir;
        }
        // Check if point is on the top cap
        if (t >= height) {
            return axisDir;
        }
        // Point is on the curved surface
        Point o = axisBase.add(axisDir.scale(t));
        return point.subtract(o).normalize();
    }
}
