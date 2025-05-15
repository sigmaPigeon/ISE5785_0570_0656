package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract class to represent a geometry object in 3D space.
 * This class defines a method to get the normal vector at a given point.
 */
public abstract class Geometry implements Intersectable {

    public abstract Vector getNormal(Point p);
}
