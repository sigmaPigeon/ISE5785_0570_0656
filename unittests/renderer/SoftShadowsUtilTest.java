package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

class SoftShadowsUtilTest {

    /** Scene for the tests */
    private final Scene scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
            .setRayTracer(scene, RayTracerType.SIMPLE);

    @Test
    void complexSceneWithManyGeometriesAndLights() {
        // Add 10 geometries with much darker emission
        scene.geometries.add(
                new Sphere(new Point(0, 0, 0), 20)
                        .setEmission(new Color(20, 20, 50))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnSh(100).setkT(0.3)),
                new Sphere(new Point(40, 0, 0), 10)
                        .setEmission(new Color(50, 20, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(50)),
                new Cylinder(new Ray(new Point(-40, 0, 0), new Vector(1, 3, 0)), 8, 30)
                        .setEmission(new Color(20, 50, 20))
                        .setMaterial(new Material().setkD(0.4).setkS(0.6).setnSh(120).setkT(0.5)),
                new Plane(new Point(0, -30, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setkR(0.5).setkD(0.5)),
                new Plane(new Point(0, 70, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setkR(0.5).setkD(0.5)),
// Back wall (YZ plane), moved further back to z = -120
                new Plane(new Point(0, -100, -150), new Vector(0, 0, 1))
                        .setEmission(new Color(30, 20, 40)) // Purplish
                        .setMaterial(new Material().setkD(0.7).setkS(0.2).setkR(0.1)),
// Right wall (XZ plane), moved further right to x = 60
                new Plane(new Point(-70, -100, 0), new Vector(-1, 0, 0))
                        .setEmission(new Color(20, 40, 30)) // Greenish
                        .setMaterial(new Material().setkD(0.7).setkS(0.2).setkR(0.1)),
                new Polygon(new Point(-50, -20, -149), new Point(40, -20, -149), new Point(40, 60, -149), new Point(-50, 60, -149))
                        .setEmission(new Color(10, 10, 10)) // Nearly black, so only reflection is visible
                        .setMaterial(new Material().setkR(1.0)),
                new Polygon(new Point(-20, -30, -20), new Point(-20, -30, 20), new Point(-20, 10, 20), new Point(-20, 10, -20))
                        .setEmission(new Color(30, 10, 40))
                        .setMaterial(new Material().setkR(0.3).setkD(0.7)),
                new Polygon(new Point(20, -30, -20), new Point(20, -30, 20), new Point(20, 10, 20), new Point(20, 10, -20))
                        .setEmission(new Color(40, 30, 10))
                        .setMaterial(new Material().setkR(0.3).setkD(0.7)),
                new Sphere(new Point(0, 20, 30), 7d)
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setkD(0.2).setkS(0.8).setnSh(300).setkT(0.7))
        );
// Move pyramid further to the left (x - 20)
        Point p1 = new Point(-60, -30, -100);
        Point p2 = new Point(-20, -30, -100);
        Point p3 = new Point(-20, -30, -60);
        Point p4 = new Point(-60, -30, -60);
// Apex above the base (upwards in +y)
        Point apex = new Point(-40, 0, -80);

// Add pyramid base (square)
        scene.geometries.add(
                new Polygon(p1, p2, p3, p4)
                        .setEmission(new Color(40, 20, 10))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0))
        );

// Add four pyramid sides (triangles)
        scene.geometries.add(
                new Triangle(p1, p2, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p2, p3, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p3, p4, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p4, p1, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0))
        );

        // Stronger spot light
        scene.lights.add(
                new SpotLight(new Color(210, 120, 120), new Point(60, 60, 100), new Vector(-1, -1, -2))
                        .setKl(0.0001).setKq(0.00001)
        );
// Stronger point light
        scene.lights.add(
                new PointLight(new Color(90, 150, 90), new Point(-50, 50, 50))
                        .setKl(0.0002).setKq(0.00002)
        );
        // Lower ambient light
        scene.setAmbientLight(new AmbientLight(new Color(20, 8, 8)).getIntensity());

        // Camera setup

        cameraBuilder
                .setLocation(new Point(40, -20, 240)) // Slightly to the right
                .setDirection(new Point(0, 10, 0), new Vector(0, 1, 0)) // Look toward the center
                .setVpDistance(400)
                .setVpSize(200, 200)
                .setResolution(800, 800)
                .setMultithreading(4) // Use 4 threads for rendering
                .build()
                .renderImage()
                .writeToImage("complexSceneWithManyGeometriesAndLights"); }


    @Test
    void complexSceneWithManyGeometriesAndLightsMP1() {
        // Add 10 geometries with much darker emission
        scene.geometries.add(
                new Sphere(new Point(0, 0, 0), 20)
                        .setEmission(new Color(20, 20, 50))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnSh(100).setkT(0.3)),
                new Sphere(new Point(40, 0, 0), 10)
                        .setEmission(new Color(50, 20, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(50)),
                new Cylinder(new Ray(new Point(-40, 0, 0), new Vector(1, 3, 0)), 8, 30)
                        .setEmission(new Color(20, 50, 20))
                        .setMaterial(new Material().setkD(0.4).setkS(0.6).setnSh(120).setkT(0.5)),
                new Plane(new Point(0, -30, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setkR(0.5).setkD(0.5)),
                new Plane(new Point(0, 70, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setkR(0.5).setkD(0.5)),
// Back wall (YZ plane), moved further back to z = -120
                new Plane(new Point(0, -100, -150), new Vector(0, 0, 1))
                        .setEmission(new Color(30, 20, 40)) // Purplish
                        .setMaterial(new Material().setkD(0.7).setkS(0.2).setkR(0.1)),
// Right wall (XZ plane), moved further right to x = 60
                new Plane(new Point(-70, -100, 0), new Vector(-1, 0, 0))
                        .setEmission(new Color(20, 40, 30)) // Greenish
                        .setMaterial(new Material().setkD(0.7).setkS(0.2).setkR(0.1)),
                new Polygon(new Point(-50, -20, -149), new Point(40, -20, -149), new Point(40, 60, -149), new Point(-50, 60, -149))
                        .setEmission(new Color(10, 10, 10)) // Nearly black, so only reflection is visible
                        .setMaterial(new Material().setkR(1.0)),
                new Polygon(new Point(-20, -30, -20), new Point(-20, -30, 20), new Point(-20, 10, 20), new Point(-20, 10, -20))
                        .setEmission(new Color(30, 10, 40))
                        .setMaterial(new Material().setkR(0.3).setkD(0.7)),
                new Polygon(new Point(20, -30, -20), new Point(20, -30, 20), new Point(20, 10, 20), new Point(20, 10, -20))
                        .setEmission(new Color(40, 30, 10))
                        .setMaterial(new Material().setkR(0.3).setkD(0.7)),
                new Sphere(new Point(0, 20, 30), 7d)
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setkD(0.2).setkS(0.8).setnSh(300).setkT(0.7))
        );
// Move pyramid further to the left (x - 20)
        Point p1 = new Point(-60, -30, -100);
        Point p2 = new Point(-20, -30, -100);
        Point p3 = new Point(-20, -30, -60);
        Point p4 = new Point(-60, -30, -60);
// Apex above the base (upwards in +y)
        Point apex = new Point(-40, 0, -80);

// Add pyramid base (square)
        scene.geometries.add(
                new Polygon(p1, p2, p3, p4)
                        .setEmission(new Color(40, 20, 10))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0))
        );

// Add four pyramid sides (triangles)
        scene.geometries.add(
                new Triangle(p1, p2, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p2, p3, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p3, p4, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0)),
                new Triangle(p4, p1, apex)
                        .setEmission(new Color(80, 60, 20))
                        .setMaterial(new Material().setkD(0.7).setkS(0.3).setnSh(30).setkT(0))
        );

        // Stronger spot light
        scene.lights.add(
                new DiskLight(new Color(210, 120, 120), new Point(60, 60, 100), new Vector(-1, -1, -2),5)
                        .setKl(0.0001).setKq(0.00001)
        );
// Stronger point light
        scene.lights.add(
                new SphereLight(new Color(90, 150, 90), new Point(-50, 50, 50),5 )
                        .setKl(0.0002).setKq(0.00002)
        );
// Lower ambient light
        scene.setAmbientLight(new AmbientLight(new Color(20, 8, 8)).getIntensity());

        // Camera setup

        Camera camera = cameraBuilder
                .setLocation(new Point(40, -20, 240)) // Slightly to the right
                .setDirection(new Point(0, 10, 0), new Vector(0, 1, 0)) // Look toward the center
                .setVpDistance(400)
                .setVpSize(200, 200)
                .setResolution(800, 800)
                .setRayTracer(scene, RayTracerType.SIMPLE)
                .setMultithreading(8) // Use 8 threads for rendering
                .build();

        ((SimpleRayTracer) camera.traceRay).setSoftShadows(true);

        camera.renderImage()
                .writeToImage("complexSceneWithManyGeometriesAndLightsMP1");
    }

}