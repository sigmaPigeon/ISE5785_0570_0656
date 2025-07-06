package geometries;

import primitives.Point;
import primitives.Ray;


import java.util.List;

/**
 * this class will represent a triangular shape in the space
 */
public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * method to get the intersection of a ray with the triangle
     * @param ray the ray to intersect with
     * @return unused
     */
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray){
        // The intersection of a ray with a triangle is the same as the intersection of the ray with the polygon
        return super.calculateIntersectionsHelper(ray);
    }
    @Override
    public void computeBoundingBox() {
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



