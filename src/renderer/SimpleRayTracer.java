package renderer;

import lighting.LightSource;
import scene.Scene;
import primitives.*;

import java.util.List;
import geometries.Intersectable.Intersection;

import static primitives.Point.ZERO;
import static primitives.Util.*;

/**
 * Implements a simple ray tracing algorithm for rendering a 3D scene.
 * Calculates color at intersection points using ambient, diffusive, and specular lighting.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

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
     * Checks if the intersection point is unshaded by any geometry.
     * This method is used to determine if the intersection point is in shadow.
     *
     * @param intersection The intersection data
     * @return True if the intersection point is unshaded, false otherwise
     */
    private boolean unshaded(Intersection intersection) {
        Vector pointToLight = intersection.lightDirection.scale(-1);
        Ray shadowRay = new Ray(intersection.point, pointToLight, intersection.normal);
        var shadowIntersections = scene.geometries.findIntersections(shadowRay);
        double lightDistance = intersection.lightSource.getDistance(intersection.point);
        if (shadowIntersections == null) {
            // No intersections with other geometries, the point is unshaded
            return true;
        }
        for (Point shadowIntersection : shadowIntersections) {
            // Check if the intersection point is closer than the light source
            if (shadowIntersection.distance(intersection.point) < lightDistance) {
                return false; // The point is in shadow
            }
        }
        return true; // The point is not in shadow
    }

    /**
     * Calculates the transparency factor (ktr) for the intersection point.
     * Determines how much light passes through transparent objects between the point and the light source.
     *
     * @param intersection The intersection data
     * @return The transparency factor as Double3
     */
    private Double3 transparency(Intersection intersection) {
        Vector pointToLight = intersection.lightDirection.scale(-1);
        Ray shadowRay = new Ray(intersection.point, pointToLight, intersection.normal);
        var shadowIntersections = scene.geometries.calculateIntersections(shadowRay);
        Double3 ktr = Double3.ONE;
        double lightDistance = intersection.lightSource.getDistance(intersection.point);
        if (shadowIntersections == null) {
            // No intersections with other geometries, the point is unshaded
            return ktr;
        }
        for (Intersection shadowIntersection : shadowIntersections) {
            // Check if the intersection point is closer than the light source
            if (shadowIntersection.point.distance(intersection.point) < lightDistance) {
                ktr = ktr.product(shadowIntersection.material.kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO; // The point is in shadow
            }
        }
        return ktr; // The point is not in shadow
    }

    /**
     * Finds the closest intersection point of a ray with the scene geometries.
     *
     * @param ray The ray to trace
     * @return The closest intersection point, or null if no intersection is found
     */
    private Intersection findClosestIntersection(Ray ray){
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null || intersections.isEmpty()) {
            return null; // No intersection found
        }
        return ray.findClosestIntersection(intersections); // Return the closest intersection
    }

    /**
     * Constructs a reflected ray at the intersection point.
     *
     * @param intersection The intersection data
     * @return The reflected ray
     */
    private Ray constructReflectedRay(Intersection intersection) {
        Vector v = intersection.direction;
        Vector n = intersection.normal;
        double nv = n.dotProduct(v);
        if (isZero(nv)) return null;
        Vector r = v.subtract(n.scale(2 * nv)).normalize();
        return new Ray(intersection.point, r, n);
    }

    /**
     * Constructs a refracted (transparency) ray at the intersection point.
     *
     * @param intersection The intersection data
     * @return The refracted ray
     */
    private Ray constructRefractedRay(Intersection intersection) {
        return new Ray(intersection.point, intersection.direction, intersection.normal);
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
        // Get the closest intersection point
        Intersection closestPoint = findClosestIntersection(ray);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at the given intersection, including ambient and local lighting effects.
     *
     * @param intersection The intersection data
     * @param ray The ray that hit the intersection
     * @return The computed color at the intersection
     */
    private Color calcColor(Intersection intersection, Ray ray) {
        if (intersection == null || !preprocessIntersection(intersection, ray.getDirection())) {
            // If the ray is inside the geometry, return the ambient light color
            return Color.BLACK;
        }
        return calcColor(intersection, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity().scale(intersection.geometry.getMaterial().kA));
    }

    /**
     * Recursively calculates the color at the intersection, including global effects.
     *
     * @param intersection The intersection data
     * @param level The recursion level
     * @param k The accumulated attenuation factor
     * @return The computed color at the intersection
     */
    private Color calcColor(Intersection intersection, int level, Double3 k) {
        Color color = calcColorLocalEffects(intersection, k);
        if (level == 1) return color;
        return color.add(calcGlobalEffects(intersection, level, k));
    }

    /**
     * Calculates the global lighting effects (reflection and refraction) at the intersection.
     *
     * @param intersection The intersection data
     * @param level The recursion level
     * @param k The accumulated attenuation factor
     * @return The color contribution from global effects
     */
    private Color calcGlobalEffects(Intersection intersection, int level, Double3 k) {
        return calcGlobalEffect(constructRefractedRay(intersection),
                level, k, intersection.material.kT)
                .add(calcGlobalEffect(constructReflectedRay(intersection),
                        level, k, intersection.material.kR));
    }

    /**
     * Calculates the color contribution from a single global effect (reflection or refraction).
     *
     * @param ray The reflected or refracted ray
     * @param level The recursion level
     * @param k The accumulated attenuation factor
     * @param kx The reflection or refraction coefficient
     * @return The color contribution from the effect
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        Intersection intersection = findClosestIntersection(ray);
        if (intersection == null) return scene.backgroundColor.scale(kx);
        return preprocessIntersection(intersection, ray.getDirection())
                ? calcColor(intersection, level - 1, kkx).scale(kx) : Color.BLACK;
    }

    /**
     * Preprocesses the intersection by updating its normal and direction.
     * Determines if the ray is inside the geometry.
     *
     * @param intersection The intersection to preprocess
     * @param direction The direction of the incoming ray
     * @return True if the ray is outside the geometry, false otherwise
     */
    public boolean preprocessIntersection(Intersection intersection, Vector direction) {
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

        return (alignZero(intersection.ldxn * intersection.dxn) > 0);
    }

    /**
     * Calculates the local lighting effects (diffusive and specular) at the intersection.
     *
     * @param intersection The intersection data
     * @param k The accumulated attenuation factor
     * @return The color contribution from all light sources
     */
    private Color calcColorLocalEffects(Intersection intersection, Double3 k) {
        Material material = intersection.geometry.getMaterial();
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            if (setLightSource(intersection, lightSource)) {
                Double3 ktr = transparency(intersection);
                if (ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    continue; // The point is in shadow
                }
                color = color.add(
                        lightSource.getIntensity(intersection.point).scale(ktr)
                                .scale(calcDiffusive(intersection)
                                        .add(calcSpecular(intersection))));
            }
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
        return intersection.material.kS.scale(Math.pow(Math.max(0, -1 * rdxn), intersection.material.nSh));
    }
}