package geometries;

import primitives.*;
import primitives.Vector;

import java.util.*;

/**
 * Abstract base class representing a geometric object in 3D space.
 * <p>
 * Provides common properties such as emission color and material,
 * and defines the interface for obtaining the normal vector at a point.
 * </p>
 */
public abstract class Geometry extends Intersectable {
    /**
     * The emission color of the geometry (the color it emits regardless of lighting).
     */
    protected Color emission = Color.BLACK;

    /**
     * The material properties of the geometry (e.g., diffuse, specular, shininess).
     */
    Material material = new Material();

    /**
     * Returns the normal vector to the geometry at the specified point.
     *
     * @param p the point on the geometry
     * @return the normal vector at the given point
     */
    public abstract Vector getNormal(Point p);

    /**
     * Gets the emission color of the geometry.
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission the emission color to set
     * @return this Geometry instance (for chaining)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Gets the material properties of the geometry.
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material properties of the geometry.
     *
     * @param material the material to set
     * @return this Geometry instance (for chaining)
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}