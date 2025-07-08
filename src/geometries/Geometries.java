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

    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        if(bvhIsOn&&!intersects(ray))
            return null;// in Geometries
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
    /**
     * Method to check if a ray intersects with any of the geometries.
     *
     * @param ray the ray to check for intersections
     * @return true if the ray intersects with any geometry, false otherwise
     */
    @Override
    public void computeBoundingBox() {
        if (geometries.isEmpty()) return;
        geometries.forEach(Intersectable::initializebox);
        bvhIsOn=true;
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

        for (Intersectable g : geometries) {
            AABB box = g.box;
            if (box == null) continue;
            Point min = box.min, max = box.max;
            if (min.getX() < minX) minX = min.getX();
            if (min.getY() < minY) minY = min.getY();
            if (min.getZ() < minZ) minZ = min.getZ();
            if (max.getX() > maxX) maxX = max.getX();
            if (max.getY() > maxY) maxY = max.getY();
            if (max.getZ() > maxZ) maxZ = max.getZ();
        }
        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));

    }
    /**
     * Method to create a hierarchy of geometries for efficient intersection calculations.
     * This method recursively pairs geometries and computes their bounding boxes until only one geometry remains.
     */
    public void createHierarchy() {
        if (geometries.size()==1) {
            geometries.getFirst().computeBoundingBox();
        }
        else {
            List<Intersectable> newGeometries=new LinkedList<>();
            for (int i = 0; i < geometries.size() - 1; i += 2) {
                Geometries tmp = new Geometries(geometries.get(i), geometries.get(i + 1));
                tmp.computeBoundingBox();
               newGeometries.add(tmp);
            }
            if (geometries.size() % 2 == 1) {
                geometries.getLast().computeBoundingBox();
                newGeometries.add(geometries.getLast());
            }
            geometries.clear();
            geometries.addAll(newGeometries); // Recursively create hierarchy until only one geometry remains
            createHierarchy();
        }


    }
}