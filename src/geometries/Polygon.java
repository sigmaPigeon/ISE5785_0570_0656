package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.abs;
import static primitives.Util.isZero;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    /**
     * The size of the polygon - the amount of the vertices in the polygon
     */
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal(null);
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[size - 1].subtract(vertices[size - 2]);
        Vector edge2 = vertices[0].subtract(vertices[size - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < size; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal(null);
    }


    /**
     * Find the intersection points of a ray with the polygon
     *
     * @param ray the ray to intersect with
     * @return list of intersection points or null if there are no intersections
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // Check if the ray intersects with the plane
        List<Point> intersections = plane.findIntersections(ray);
        if (intersections == null) return null; // The ray is parallel to the plane
        Point intersection = intersections.getFirst(); // Get the first intersection point
        // Check if the intersection point is inside the polygon
        if (intersection.equals(vertices.get(0)) || intersection.equals(vertices.get(1)))
            return null; // The intersection point is a vertex of the polygon
        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        // Calculate the normal vector of the polygon using the first two edges
        Vector n1 = v1.crossProduct(v2).normalize();
        int flag = n1.dotProduct(ray.getDirection()) > 0 ? 1 : -1;
        //
        for (int i = 1; i < size; ++i) {
            if (intersection.equals(vertices.get((i+1)%size)))
                return null;// The intersection point is a vertex of the polygon
            v1 = vertices.get(i).subtract(ray.getHead());
            v2 = vertices.get((i + 1) % size).subtract(ray.getHead());
            n1 = v1.crossProduct(v2).normalize();
            if(n1.dotProduct(ray.getDirection()) > 0)
                flag += 1;
            if (n1.dotProduct(ray.getDirection()) < 0)
                flag -= 1;
            if (n1.dotProduct(ray.getDirection()) == 0)
                return null;
        }
        if (abs(flag) == size) return List.of(new Intersection(this,intersection));
        // The intersection point is outside the polygon
        return null;
    }
    @Override
    public void computeBoundingBox() {
        bvhIsOn= true;
        double minX = vertices.get(0).getX(), maxX = vertices.get(0).getX();
        double minY = vertices.get(0).getY(), maxY = vertices.get(0).getY();
        double minZ = vertices.get(0).getZ(), maxZ = vertices.get(0).getZ();

        for (Point p : vertices) {
            double x = p.getX(), y = p.getY(), z = p.getZ();
            if (x < minX) minX = x; if (x > maxX) maxX = x;
            if (y < minY) minY = y; if (y > maxY) maxY = y;
            if (z < minZ) minZ = z; if (z > maxZ) maxZ = z;
        }
        box = new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }
}
