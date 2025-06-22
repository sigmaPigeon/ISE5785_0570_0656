package lighting;

import primitives.Color;

/**
 * The Light class represents a base light with a specific color intensity.
 * This class is intended to be extended by specific types of lights.
 */
class Light {
    /**
     * The color intensity of the light.
     */
    protected final Color intensity;

    /**
     * Constructs a Light with the specified color intensity.
     *
     * @param intensity the color intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the color intensity of the light.
     *
     * @return the color intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}