package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Abstract class to represent a geometry object in 3D space.
 * This class defines a method to get the normal vector at a given point.
 */
public abstract class Geometry implements Intersectable {
    public abstract Vector getNormal(Point p);

}
