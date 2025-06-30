package lighting;

import primitives.*;

/**
 * The LightSource interface represents a light source in a 3D scene.
 * Implementing classes provide methods to get the intensity and direction of light at a given point.
 */
public interface LightSource {
    /**
     * Returns the intensity of the light at the specified point.
     *
     * @param p the point to calculate the intensity at
     * @return the color intensity at the given point
     */
    Color getIntensity(Point p);

    /**
     * Returns the normalized direction vector from the light source to the specified point.
     *
     * @param p the point to calculate the direction to
     * @return the normalized direction vector
     */
    Vector getL(Point p);
    /**
     * Returns the distance from the light source to the specified point.
     *
     * @param p the point to calculate the distance to
     * @return the distance to the point
     */
    double getDistance(Point p);
}