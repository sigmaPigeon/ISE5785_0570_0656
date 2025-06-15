package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource{
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

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

}
