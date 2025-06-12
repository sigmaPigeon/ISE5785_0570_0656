package renderer;

import scene.Scene;
import primitives.*;

import java.util.List;
import geometries.Intersectable. Intersection;
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructor for SimpleRayTracer.
     * Initializes the ray tracer with the provided scene.
     *
     * @param scene The scene to be rendered
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Renders the scene using a simple ray tracing algorithm.
     */
    @Override
    public Color traceRay(Ray ray){
        // Find the closest intersection point of the ray with the scene
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections==null || intersections.isEmpty()) {
            // If there are no intersections, return the background color
            return scene.backgroundColor;
        }
        // Get the closest intersection point
        Intersection closestPoint = ray.findClosestIntersection(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(Intersection intersection) {
        return scene
                .ambientLight.getIntensity().scale(intersection.geometry.mat).add(gp.geometry.getEmission());
    }}
