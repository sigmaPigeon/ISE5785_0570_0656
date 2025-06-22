package geometries;

/**
 * interface class to define a geometry object that uses radius in its calculations
 */
abstract public class RadialGeometry extends Geometry
{
    protected final double radius;

    /**
     * constructor to initialize the radius of a radial geometry object
     * @param r the radius
     */
    public RadialGeometry(double r){
        radius = r;
    }
}
