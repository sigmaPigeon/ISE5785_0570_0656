package renderer;
import primitives.*;
import geometries.*;
import renderer.Camera;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CameraIntersectionsIntegrationTests {
    @Test
    void CameraIntegrationSphereTest() {
        Sphere tc1=new Sphere (new Point(0,0,3),1);

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
}
