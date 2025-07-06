package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.concurrent.ThreadLocalRandom;

import static primitives.Vector.AXIS_Y;
import static primitives.Vector.AXIS_Z;

public class DiskLight extends SpotLight{
    private final double radius;

    /**
     * Constructs a new DiskLight with the given intensity, position, direction, and radius.
     *
     * @param intensity the color intensity of the light
     * @param position  the position of the light source
     * @param direction the direction vector of the spotlight beam
     * @param radius    the radius of the disk
     */
    public DiskLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position, direction);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Point getRandomPointOnSurface() {
        if (radius == 0 || super.direction == null)
            return getPosition();

        Vector direction = super.direction.normalize();

        Vector dir = direction.normalize();
        Vector vUp = dir.crossProduct(AXIS_Y);
        if (vUp.lengthSquared() == 0) {
            vUp = dir.crossProduct(AXIS_Z); // if dir is parallel to Y, use Z
        }
        vUp = vUp.normalize();
        Vector vRight = dir.crossProduct(vUp).normalize();


        double r = radius * Math.sqrt(ThreadLocalRandom.current().nextDouble());
        double theta = 2 * Math.PI * ThreadLocalRandom.current().nextDouble();

        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);

        return getPosition()
                .add(vRight.scale(x))
                .add(vUp.scale(y));
    }

}
