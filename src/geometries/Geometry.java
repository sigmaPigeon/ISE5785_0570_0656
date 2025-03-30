package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface class to define a geometry object
 */
public abstract class Geometry {
    public abstract Vector getNormal(Point p);
}
