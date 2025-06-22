package renderer;

import lighting.LightSource;
import scene.Scene;
import primitives.*;

import java.util.List;
import geometries.Intersectable. Intersection;

import static primitives.Util.*;

/**
 * Implements a simple ray tracing algorithm for rendering a 3D scene.
 * Calculates color at intersection points using ambient, diffusive, and specular lighting.
 */
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
     * Traces a ray through the scene and returns the color at the closest intersection point.
     * If no intersection is found, returns the background color.
     *
     * @param ray The ray to trace
     * @return The color at the intersection point or the background color if no intersection occurs
     */
    @Override
    public Color traceRay(Ray ray) {
        // Find the closest intersection point of the ray with the scene
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            // If there are no intersections, return the background color
            return scene.backgroundColor;
        }
        // Get the closest intersection point
        Intersection closestPoint = ray.findClosestIntersection(intersections);
        return calcColor(closestPoint,ray);
    }

    /**
     * Calculates the color at the given intersection, including ambient and local lighting effects.
     *
     * @param intersection The intersection data
     * @param ray The ray that hit the intersection
     * @return The computed color at the intersection
     */
    private Color calcColor(Intersection intersection,Ray ray) {
        if(!preprocessIntersection(intersection , ray.getDirection())) {
            // If the ray is inside the geometry, return the ambient light color
            return Color.BLACK;
        }
        return scene.ambientLight.getIntensity().scale(intersection.geometry.getMaterial().kA)
                .add(calcColorLocalEffects(intersection));
    }

    /**
     * Preprocesses the intersection by updating its normal and direction.
     * Determines if the ray is inside the geometry.
     *
     * @param intersection The intersection to preprocess
     * @param direction The direction of the incoming ray
     * @return True if the ray is outside the geometry, false otherwise
     */
    public boolean preprocessIntersection(Intersection intersection,Vector direction) {
        intersection.direction = direction;
        intersection.normal = intersection.geometry.getNormal(intersection.point);
        intersection.dxn = intersection.normal.dotProduct(direction);
        if (isZero(intersection.dxn)) {
            // If the ray is inside the geometry, reverse the normal
            return false;
        }
        return true;
    }

    /**
     * Sets the light source and computes the light direction for the intersection.
     * Checks if the light is on the correct side of the surface.
     *
     * @param intersection The intersection to update
     * @param lightSource The light source being considered
     * @return True if the light contributes to the intersection, false otherwise
     */
    public boolean setLightSource(Intersection intersection, LightSource lightSource) {
        intersection.lightSource = lightSource;
        intersection.lightDirection = lightSource.getL(intersection.point);

        intersection.ldxn = intersection.lightDirection.dotProduct(intersection.geometry.getNormal(intersection.point));

        return(alignZero(intersection.ldxn* intersection.dxn) > 0);
    }

    /**
     * Calculates the local lighting effects (diffusive and specular) at the intersection.
     *
     * @param intersection The intersection data
     * @return The color contribution from all light sources
     */
    private Color calcColorLocalEffects(Intersection intersection) {
        Material material= intersection.geometry.getMaterial();
        Color color =intersection.geometry.getEmission();
        for(LightSource lightSource:scene.lights){
            if(!setLightSource(intersection, lightSource)) {
                continue;// Skip if the light source is parallel to the normal
            }
            color = color.add(
                    lightSource.getIntensity(intersection.point)
                            .scale(calcDiffusive(intersection)
                                    .add(calcSpecular(intersection))));
        }
        return color;

    }

    /**
     * Calculates the diffusive reflection component at the intersection.
     *
     * @param intersection The intersection data
     * @return The diffusive reflection coefficient
     */
    private Double3 calcDiffusive(Intersection intersection) {
        return intersection.geometry.getMaterial().kD.scale(Math.abs(intersection.ldxn));
    }

    /**
     * Calculates the specular reflection component at the intersection.
     *
     * @param intersection The intersection data
     * @return The specular reflection coefficient
     */
    private Double3 calcSpecular(Intersection intersection) {
        Vector r = intersection.lightDirection.subtract(intersection.normal.scale(2 * intersection.ldxn)).normalize();
        double rdxn = r.dotProduct(intersection.direction);
        return intersection.material.kS.scale(Math.pow(Math.max(0,-1*rdxn), intersection.material.nSh));
    }
}