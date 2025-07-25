package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author Dan Zilberstein
 */
class ReflectionRefractionTests {
   /** Default constructor to satisfy JavaDoc generator */
   ReflectionRefractionTests() { /* to satisfy JavaDoc generator */ }

   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()     //
      .setRayTracer(scene, RayTracerType.SIMPLE);

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   void twoSpheres() {
      scene.geometries.add( //
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setkD(0.4).setkS(0.3).setnSh(100).setkT(0.3)), //
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnSh(100))); //
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      cameraBuilder
         .setLocation(new Point(0, 0, 1000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(1000).setVpSize(150, 150) //
         .setResolution(500, 500) //
         .build() //
         .renderImage() //
         .writeToImage("refractionTwoSpheres");
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   void twoSpheresOnMirrors() {
      scene.geometries.add( //
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnSh(20) //
                                 .setkT(new Double3(0.5, 0, 0))), //
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnSh(20)), //
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setkR(1)), //
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(26, 26, 26)).getIntensity());
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      cameraBuilder
         .setLocation(new Point(0, 0, 10000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(10000).setVpSize(2500, 2500) //
         .setResolution(500, 500) //
         .build() //
         .renderImage() //
         .writeToImage("reflectionTwoSpheresMirrored");
   }

   /**
    * Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow
    */
   @Test
   void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnSh(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnSh(60)),
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setkD(0.2).setkS(0.2).setnSh(30).setkT(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(38, 38, 38)).getIntensity());
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setKl(4E-5).setKq(2E-7));

      cameraBuilder
         .setLocation(new Point(0, 0, 1000)) //
         .setDirection(Point.ZERO, Vector.AXIS_Y) //
         .setVpDistance(1000).setVpSize(200, 200) //
         .setResolution(600, 600) //
         .build() //
         .renderImage() //
         .writeToImage("refractionShadow");
   }
    /**
     * Produce a picture of a scene with multiple geometries, including spheres,
     * triangles, and different materials.
     */
   @Test
   void demoSceneWithMultipleGeometries() {
      // Glass sphere (center left)
      scene.geometries.add(
              new Sphere(new Point(-60, 0, -120), 35d)
                      .setEmission(new Color(0, 120, 255))
                      .setMaterial(new Material()
                              .setkD(0.2).setkS(0.7).setnSh(200)
                              .setkT(0.8)), // transparency
              // Metallic sphere (center right)
              new Sphere(new Point(60, 0, -100), 35d)
                      .setEmission(new Color(255, 80, 80))
                      .setMaterial(new Material()
                              .setkD(0.3).setkS(0.7).setnSh(150)
                              .setkR(0.7)), // reflection
              // Proportional reflective triangle (bottom)
              new Triangle(
                      new Point(-80, -80, -160),
                      new Point(80, -80, -160),
                      new Point(0, -10, -200))
                      .setEmission(new Color(40, 220, 40))
                      .setMaterial(new Material()
                              .setkD(0.4).setkS(0.6).setnSh(100)
                              .setkR(0.6)), // reflection
              // Proportional colored triangle (background)
              new Triangle(
                      new Point(-90, 90, -200),
                      new Point(90, 90, -200),
                      new Point(0, 160, -250))
                      .setEmission(new Color(255, 220, 40))
                      .setMaterial(new Material()
                              .setkD(0.6).setkS(0.4).setnSh(60))
      );
      scene.setAmbientLight(new AmbientLight(new Color(40, 40, 40)).getIntensity());
      scene.lights.add(
              new SpotLight(new Color(1000, 600, 600), new Point(-120, 120, 150), new Vector(1, -1, -2))
                      .setKl(0.0002).setKq(0.00002)
      );
      scene.lights.add(
              new PointLight(new Color(300, 300, 600), new Point(80, -80, 100))
                      .setKl(0.0005).setKq(0.00005)
      );

      cameraBuilder
              .setLocation(new Point(0, 20, 350))
              .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0))
              .setVpDistance(350)
              .setVpSize(220, 220)
              .setResolution(500, 500)
              .build()
              .renderImage()
              .writeToImage("demoMultiGeometry");
   }


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

      // Add 3 much dimmer light sources
      scene.lights.add(
              new SpotLight(new Color(70, 40, 40), new Point(60, 60, 100), new Vector(-1, -1, -2))
                      .setKl(0.0001).setKq(0.00001)
      );
      scene.lights.add(
              new PointLight(new Color(30, 50, 30), new Point(-50, 50, 50))
                      .setKl(0.0002).setKq(0.00002)
      );
      scene.lights.add(
              new DirectionalLight(new Color(20, 20, 40), new Vector(1, -1, -1))
      );

      // Lower ambient light
      scene.setAmbientLight(new AmbientLight(new Color(30, 30, 30)).getIntensity());

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
              .writeToImage("complexSceneWithManyGeometriesAndLights")

      ; }
}
