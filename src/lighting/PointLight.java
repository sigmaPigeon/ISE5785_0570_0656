package lighting;

import primitives.*;

/**
 * The PointLight class represents a point light source in 3D space.
 * The light's intensity decreases with distance according to attenuation factors.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The position of the point light in 3D space.
     */
    private final Point position;

    /**
     * Constant attenuation factor.
     */
    private double kC = 1;

    /**
     * Linear attenuation factor.
     */
    private double kL = 0;

    /**
     * Quadratic attenuation factor.
     */
    private double kQ = 0;

    /**
     * Constructor to initialize the point light with intensity and position.
     *
     * @param intensity the intensity of the point light
     * @param position  the position of the point light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Calculates the intensity of the light at a given point,
     * considering distance attenuation.
     *
     * @param p the point to calculate the intensity at
     * @return the color intensity at the given point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        double attenuation = 1.0 / (kC + kL * d + kQ * d * d);
        return intensity.scale(attenuation);
    }

    /**
     * Returns the normalized direction vector from the light to the given point.
     *
     * @param p the point to calculate the direction to
     * @return the normalized direction vector
     */
    @Override
    public Vector getL(Point p) {
        return position.subtract(p).normalize().scale(-1);
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }

    /**
     * Sets the constant attenuation factor.
     *
     * @param kC the constant attenuation factor
     * @return this PointLight instance (for chaining)
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     *
     * @param kL the linear attenuation factor
     * @return this PointLight instance (for chaining)
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ the quadratic attenuation factor
     * @return this PointLight instance (for chaining)
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

}