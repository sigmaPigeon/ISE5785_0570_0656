package lighting;

import primitives.Color;

import static primitives.Color.BLACK;

/**
 * The AmbientLight class represents ambient light in a 3D scene.
 * Ambient light is a global light that affects all objects equally,
 * simulating indirect scattered light.
 */
public class AmbientLight extends Light {
    /**
     * A constant representing no ambient light (black).
     */
    public static final Color NONE = BLACK;

    /**
     * Constructs an AmbientLight with the specified color intensity.
     *
     * @param intensity the intensity of the ambient light
     */
    public AmbientLight(Color intensity) {
        super(intensity);
    }
}