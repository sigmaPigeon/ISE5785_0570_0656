package lighting;


import primitives.Color;

import static primitives.Color.BLACK;

public class AmbientLight {
    static public final Color NONE = BLACK;
    private final Color intensity;

    /**
     * Constructor to initialize the ambient light with intensity and color.
     *
     * @param intensity the intensity of the ambient light
     */
    public AmbientLight(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Method to get the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
