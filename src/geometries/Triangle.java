package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this class will represent a triangular shape in the space
 */
public class Triangle extends Polygon {
    Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * method to get the intersection of a ray with the triangle
     * @param ray the ray to intersect with
     * @return unused
     */
    @Override
    public List<Point> findIntersections(Ray ray){
        // The intersection of a ray with a triangle is the same as the intersection of the ray with the polygon
        return super.findIntersections(ray);
    }
}



