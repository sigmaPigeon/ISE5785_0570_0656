package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.concurrent.ThreadLocalRandom;

import static primitives.Vector.AXIS_Y;
import static primitives.Vector.AXIS_Z;

/**
 * SphereLight represents a point light source with a spherical area for soft shadow effects.
 * The light emits from a sphere surface, allowing for more realistic soft shadows.
 */
public class SphereLight extends PointLight {
    private final double radius;

    /**
     * Constructs a new SphereLight with the given intensity, position, and radius.
     *
     * @param intensity the color intensity of the light
     * @param position  the position of the light source
     * @param radius    the radius of the sphere
     */
    public SphereLight(Color intensity, Point position, double radius) {
        super(intensity, position);
        this.radius = radius;
    }

    /**
     * Returns a random point on the surface of the sphere for soft shadow sampling.
     * If the radius is zero, returns the light's position.
     *
     * @return a random point on the sphere surface
     */
    @Override
    public Point getRandomPointOnSurface() {
        if (radius == 0)
            return getPosition();

        // Generate a random point on the unit sphere using spherical coordinates
        double u = ThreadLocalRandom.current().nextDouble();
        double v = ThreadLocalRandom.current().nextDouble();

        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2 * v - 1);

        double x = radius * Math.sin(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi) * Math.sin(theta);
        double z = radius * Math.cos(phi);

        return getPosition().add(new Vector(x, y, z));
    }
}