package lighting;

import primitives.*;

public class SpotLight extends PointLight {

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

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(super.getL(p))));
    }
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }



}
