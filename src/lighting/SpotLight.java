package lighting;

import primitives.*;

/**
 * The SpotLight class represents a point light source with a specific direction,
 * creating a cone-shaped beam of light.
 * Extends {@link PointLight} and adds directionality to the light's intensity.
 */
public class SpotLight extends PointLight {

    /**
     * The direction of the spotlight beam.
     */
    private final Vector direction;

    /**
     * Constructor to initialize the spot light with intensity, position, and direction.
     *
     * @param intensity the intensity of the light
     * @param position  the position of the light source
     * @param direction the direction of the light beam
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Calculates the intensity of the spotlight at a given point,
     * considering the direction of the beam.
     *
     * @param p the point to calculate the intensity at
     * @return the color intensity at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(super.getL(p))));
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return this SpotLight instance (for chaining)
     */
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return this SpotLight instance (for chaining)
     */
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return this SpotLight instance (for chaining)
     */
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }
}