package geometries;

import primitives.*;
import primitives.Vector;


import java.util.*;


/**
 * Abstract class to represent a geometry object in 3D space.
 * This class defines a method to get the normal vector at a given point.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    Material material = new Material();

    public abstract Vector getNormal(Point p);
    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    public Material getMaterial() {
        return material;
    }
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }



}
