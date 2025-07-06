package lighting;

import primitives.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a spotlight, which is a point light source with a specific direction,
 * creating a cone-shaped beam of light. The intensity at a point depends on the
 * direction and the narrowness of the beam.
 * <p>
 * Extends {@link PointLight} and adds directionality and beam control.
 * </p>
 */
public class SpotLight extends PointLight {
    /**
     * The normalized direction vector of the spotlight beam.
     */
    protected final Vector direction;

    /**
     * Controls the narrowness of the spotlight beam.
     * Higher values produce a narrower, more focused beam.
     */
    private double narrowBeam = 1.0;

    /**
     * Constructs a new SpotLight with the given intensity, position, and direction.
     *
     * @param intensity the color intensity of the light
     * @param position  the position of the light source
     * @param direction the direction vector of the spotlight beam
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Calculates the intensity of the spotlight at a given point,
     * considering the direction and narrowness of the beam.
     *
     * @param p the point to calculate the intensity at
     * @return the color intensity at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        double projection = Math.max(0, direction.dotProduct(super.getL(p)));
        double factor = Math.pow(projection, narrowBeam);
        return super.getIntensity(p).scale(factor);
    }

    /**
     * Sets the narrowness of the spotlight beam.
     * Higher values make the beam narrower and more focused.
     *
     * @param narrowBeam the narrowness factor (must be >= 1)
     * @return this SpotLight instance (for chaining)
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
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
    /**
     * Returns a random point on the surface of the spotlight's position.
     * This method can be overridden if the spotlight has a specific surface area.
     *
     * @return the position of the spotlight
     */
    @Override
    public Point getRandomPointOnSurface() {
        // For a spotlight, we can return the position as the surface point
        return super.getRandomPointOnSurface();
    }
}