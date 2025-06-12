package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    private final List<Intersectable> geometries = new ArrayList<>();

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
     * @param geometries the geometries to add
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries) {
            this.geometries.add(geometry);
        }
    }

    /**
     * Method to get the list of geometries.
     *
     * @return the list of geometries
     */
    public List<Intersectable> getGeometries() {
        return this.geometries;
    }

    public List<Intersection> calculateIntersectionsHelper(Ray ray) { // in Geometries
        List<Intersection> totalList = null;
        for (Intersectable geometry : geometries) {
            var list = geometry.calculateIntersections(ray);
            if (list != null)
                if (totalList == null)
                    totalList = new LinkedList<>(list);
                else
                    totalList.addAll(list);
        }
        return totalList;
    }
}