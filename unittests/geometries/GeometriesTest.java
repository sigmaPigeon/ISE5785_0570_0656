package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        //============ Equivalence Partitions Tests ==============
        // TC01: Test the intersection of some geometries with a ray(not all)
        Triangle triangle011 = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        Triangle triangle012 = new Triangle(new Point(0, 0, 1), new Point(1, 0, 1), new Point(0, 1, 1));
        Sphere sphere011 = new Sphere(new Point(1, 1, 1), 1);
        Sphere sphere012 = new Sphere(new Point(2, 8, 2), 1);
        Geometries geometries01 = new Geometries(triangle011, triangle012, sphere011, sphere012);
        Ray ray011 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        List<Point> expectedIntersections = geometries01.findIntersections(ray011);
        assertEquals(4, expectedIntersections.size(), "Wrong number of intersection points");

        //============== Boundary Values Tests ==================
        // TC10: Test the intersection of some geometries with a ray(all)
        Sphere sphere102 = new Sphere(new Point(2, 1, 2), 1);
        Geometries geometries10 = new Geometries(triangle011, triangle012, sphere011, sphere102);//using the 3 geometries from earlier
        Ray ray101 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        List<Point> expectedIntersections10 = geometries10.findIntersections(ray101);
        assertEquals(6, expectedIntersections10.size(), "Wrong number of intersection points");

        // TC11: Test the intersection of some geometries with a ray(none)
        Ray ray111 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, -1));
        List<Point> expectedIntersections11 = geometries10.findIntersections(ray111);
        assertNull(expectedIntersections11, "Expected no intersection points");

        // TC12: Test the intersection of some geometries with a ray(1)
        Ray ray121 = new Ray(new Point(0.5, 0.5, 0.5), new Vector(0, 0, -1));
        List<Point> expectedIntersections12 = geometries10.findIntersections(ray121);
        assertEquals(1, expectedIntersections12.size(), "Wrong number of intersection points");

        // TC13: Test the intersection of an empty list of geometries
        Geometries emptyGeometries = new Geometries();
        Ray ray131 = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        List<Point> expectedIntersections13 = emptyGeometries.findIntersections(ray131);
        assertNull(expectedIntersections13, "Expected no intersection points");
    }
}