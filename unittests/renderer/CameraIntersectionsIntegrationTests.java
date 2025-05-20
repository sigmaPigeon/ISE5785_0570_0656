package renderer;

import primitives.*;
import geometries.*;
import renderer.Camera;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the Camera class to verify intersections with various geometries.
 */
public class CameraIntersectionsIntegrationTests {

    /**
     * Test integration of the Camera with a Sphere geometry.
     * Verifies the number of intersection points for different sphere configurations.
     */
    @Test
    void CameraIntegrationSphereTest() {
        Camera camera1 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0, 0, -1))
                .setVpSize(3, 3)
                .setResolution(3, 3)
                .setVpDistance(1)
                .build();

        Sphere tc1 = new Sphere(new Point(0, 0, -3), 1);
        check(2, findAllIntersections(camera1, tc1));

        Sphere tc2 = new Sphere(new Point(0, 0, -3), 2.5);
        check(18, findAllIntersections(camera1, tc2));

        Sphere tc3 = new Sphere(new Point(0, 0, -2.5), 2);
        check(10, findAllIntersections(camera1, tc3));

        Sphere tc4 = new Sphere(new Point(0, 0, -1), 4);
        check(9, findAllIntersections(camera1, tc4));
    }

    /**
     * Test integration of the Camera with a Triangle geometry.
     * Verifies the number of intersection points for different triangle configurations.
     */
    @Test
    void CameraIntegrationTriangleTest() {
        Camera camera2 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0, 0, -3))
                .setVpSize(3, 3)
                .setResolution(3, 3)
                .setVpDistance(1)
                .build();

        Triangle tc1 = new Triangle(new Point(-1, -1, -2), new Point(1, -1, -2), new Point(0, 1, -2));
        check(1, findAllIntersections(camera2, tc1));

        Triangle tc2 = new Triangle(new Point(-1, -1, -2), new Point(1, -1, -2), new Point(0, 20, -2));
        check(2, findAllIntersections(camera2, tc2));
    }

    /**
     * Test integration of the Camera with a Plane geometry.
     * Verifies the number of intersection points for different plane configurations.
     */
    @Test
    void CameraIntegrationPlaneTest() {
        Camera camera3 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0, 0, -3))
                .setVpSize(3, 3)
                .setResolution(3, 3)
                .setVpDistance(1)
                .build();

        Plane tc1 = new Plane(new Point(0, 0, -2), new Vector(0, 0, -1));
        check(9, findAllIntersections(camera3, tc1));

        Plane tc2 = new Plane(new Point(0, 0, -2), new Vector(1, 0, -1));
        check(6, findAllIntersections(camera3, tc2));
    }

    /**
     * Finds all intersection points between the camera's rays and a given geometry.
     *
     * @param camera   The camera instance.
     * @param geometry The geometry to test intersections with.
     * @return The total number of intersection points.
     */
    int findAllIntersections(Camera camera, Geometry geometry) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                Ray ray = camera.constructRay(camera.getnX(), camera.getnY(), j, i);
                List<Point> intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }
        return count;
    }

    /**
     * Asserts that the actual number of intersections matches the expected value.
     * Helps in verifying the correctness of the intersection calculations. and keeps the code clean from repeated assertions.
     * @param expected The expected number of intersections.
     * @param actual   The actual number of intersections.
     */
    void check(int expected, int actual) {
        assertEquals(expected, actual, "Wrong number of intersections");
    }
}