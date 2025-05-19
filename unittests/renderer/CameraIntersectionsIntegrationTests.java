package renderer;
import primitives.*;
import geometries.*;
import renderer.Camera;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntersectionsIntegrationTests {
    @Test
    void CameraIntegrationSphereTest() {
        Camera camera1 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0,0,-3))
                .setVpSize(3,3)
                .setVpDistance(1)
                .build();

        Sphere tc1=new Sphere (new Point(0,0,-3),1);
        check(2, findAllIntersections(camera1, tc1));

        Sphere tc2=new Sphere (new Point(0,0,-2.5),2.5);
        check(18, findAllIntersections(camera1, tc2));

        Sphere tc3=new Sphere (new Point(0,0,-2),2);
        check(10, findAllIntersections(camera1, tc3));

        Sphere tc4=new Sphere (new Point(0,0,-1),4);
        check(9, findAllIntersections(camera1, tc4));


    }

    @Test
    void CameraIntegrationTriangleTest() {
        Camera camera2 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0,0,-3))
                .setVpSize(3,3)
                .setVpDistance(1)
                .build();

        Triangle tc1=new Triangle(new Point(-1,-1,-2),new Point(1,-1,-2),new Point(0,1,-2));
        check(1, findAllIntersections(camera2, tc1));

        Triangle tc2=new Triangle(new Point(-1,-1,-2),new Point(1,-1,-2),new Point(0,20,-2));
        check(2, findAllIntersections(camera2, tc2));


    }
    @Test
    void CameraIntegrationPlaneTest() {
        Camera camera3 = new Camera().getBuilder()
                .setLocation(Point.ZERO)
                .setDirection(new Point(0,0,-3))
                .setVpSize(3,3)
                .setVpDistance(1)
                .build();

        Plane tc1=new Plane(new Point(0,0,-2),new Vector(0,0,-1));
        check(9, findAllIntersections(camera3, tc1));

        Plane tc2=new Plane(new Point(0,0,-2),new Vector(1,0,-1));
        check(6, findAllIntersections(camera3, tc2));

    }

    int findAllIntersections(Camera camera, Geometry geometry) {
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                Ray ray = camera.constructRay(camera.getnX(),camera.getnY(),j, i);
                List<Point> intersections = geometry.findIntersections(ray);
                count += intersections.size();
            }
        }
        return count;
    }
    void check(int expected, int actual) {
        assertEquals(expected, actual, "Wrong number of intersections");
    }
}
