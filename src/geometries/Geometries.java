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

    @Override
    public void computeBoundingBox() {
        if (geometries.isEmpty()) return;
        geometries.forEach(Intersectable::computeBoundingBox);

        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

        for (Intersectable g : geometries) {
            AABB box = g.box;
            if (box == null) continue;
            Point min = box., max = box.max;
            if (min.getX() < minX) minX = min.getX();
            if (min.getY() < minY) minY = min.getY();
            if (min.getZ() < minZ) minZ = min.getZ();
            if (max.getX() > maxX) maxX = max.getX();
            if (max.getY() > maxY) maxY = max.getY();
            if (max.getZ() > maxZ) maxZ = max.getZ();
        }
        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));

    }
}