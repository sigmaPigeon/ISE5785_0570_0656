package lighting;

import primitives.*;

/**
 * The DirectionalLight class represents a light source with parallel rays,
 * like sunlight, defined by a direction and intensity.
 * The light's intensity does not decrease with distance.
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * The direction of the light rays.
     */
    private final Vector direction;

    /**
     * Constructor to initialize the directional light with intensity and direction.
     *
     * @param intensity the intensity of the directional light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at the specified point.
     * For directional light, intensity is constant everywhere.
     *
     * @param p the point to calculate the intensity at
     * @return the color intensity at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * Returns the normalized direction vector of the light.
     *
     * @param p the point to calculate the direction to
     * @return the normalized direction vector
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY; // Directional light is considered to be infinitely far away
    }
}