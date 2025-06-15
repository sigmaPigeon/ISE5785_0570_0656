package renderer;

import lighting.LightSource;
import scene.Scene;
import primitives.*;

import java.util.List;
import geometries.Intersectable. Intersection;

import static primitives.Util.*;

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

    private Color calcColor(Intersection intersection,Ray ray) {
        if(!preprocessIntersection(intersection , ray.getDirection())) {
            // If the ray is inside the geometry, return the ambient light color
            return Color.BLACK


                    ;
        }
        return scene.ambientLight.getIntensity().scale(intersection.geometry.getMaterial().kA)
                .add(calcColorLocalEffects(intersection));
    }

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
    public boolean setLightSource(Intersection intersection, LightSource lightSource) {
        intersection.lightSource = lightSource;
        intersection.lightDirection = lightSource.getL(intersection.point);

        intersection.ldxn = intersection.lightDirection.dotProduct(intersection.geometry.getNormal(intersection.point));

        return(alignZero(intersection.ldxn* intersection.dxn) > 0);
    }

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
    private Double3 calcDiffusive(Intersection intersection) {
        return intersection.geometry.getMaterial().kD.scale(Math.abs(intersection.ldxn));
    }
    private Double3 calcSpecular(Intersection intersection) {
        Vector r = intersection.lightDirection.subtract(intersection.normal.scale(2 * intersection.ldxn)).normalize();
        double rdxn = r.dotProduct(intersection.direction);
        return intersection.material.kS.scale(Math.pow(Math.max(0,-1*rdxn), intersection.material.nSh));
    }
}
