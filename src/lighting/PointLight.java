package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource {
    private final Point position;
    private double kC=1; // constant attenuation
    private double kL=0; // linear attenuation
    private double kQ=0; // quadratic attenuation

    /**
     * Constructor to initialize the point light with intensity, position, and attenuation factors.
     *
     * @param intensity the intensity of the point light
     * @param position  the position of the point light
     *
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        double attenuation = 1.0 / (kC + kL * d + kQ * d * d);
        return intensity.scale(attenuation);
    }

    @Override
    public Vector getL(Point p) {
        return position.subtract(p).normalize().scale(-1);
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }


}
