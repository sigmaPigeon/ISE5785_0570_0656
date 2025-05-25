package renderer;

import primitives.*;
import scene.Scene;
/** * Abstract base class for ray tracing in a 3D scene.
 * This class provides a structure for ray tracing algorithms and can be extended to implement specific ray tracing logic.
 */
public abstract class RayTracerBase {
    /** The scene in which the ray tracing will occur. */
    protected Scene scene;
    /**
     * Constructor to initialize the ray tracer with a scene.
     *
     * @param scene the scene in which the ray tracing will occur
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Method to trace a ray in the scene and return the color at the intersection point.
     * This method should be overridden by subclasses to provide specific ray tracing logic.
     *
     * @param ray the ray to trace
     * @return the color at the intersection point, or black if no intersection occurs
     */
     public abstract Color traceRay(Ray ray);

}
