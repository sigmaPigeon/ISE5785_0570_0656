package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries  {
    private List<Intersectable> geometries = new ArrayList<>();

    /**
     * Constructor to initialize the list of geometries.
     */
    public Geometries() {

    }
    /**
     * Constructor to initialize the list of geometries with a variable number of geometries.
     *
     * @param geometries the geometries to add
     */
    public Geometries(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    /**
     * Method to add a geometry to the list.
     *
     * @param geometry the geometry to add
     */
    public void add(Intersectable geometry) {
        this.geometries.add(geometry);
    }

    /**
     * Method to get the list of geometries.
     *
     * @return the list of geometries
     */
    public List<Intersectable> getGeometries() {
        return this.geometries;
    }

    public List<Point> findIntersections(Ray ray) {
        if(ray == null || geometries.isEmpty()){
            return null;
        }
        List<Point> intersections = new ArrayList<>();
        // Iterate through each geometry and find intersections with the ray
        for (Intersectable geometry : geometries) {
            // Find intersections with the current geometry
            List<Point> geometryIntersections = geometry.findIntersections(ray);
            if (geometryIntersections != null) {
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections.isEmpty() ? null : intersections;
    }
}
