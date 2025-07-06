package lighting;

import primitives.*;
import java.util.concurrent.ThreadLocalRandom;

public class SphereLight extends PointLight {
    private final double radius;

    public SphereLight(Color intensity, Point center, double radius) {
        super(intensity, center);
        this.radius = radius;
    }

    @Override
    public Point getRandomPointOnSurface() {
        double r = radius * Math.sqrt(ThreadLocalRandom.current().nextDouble());
        double theta = 2 * Math.PI * ThreadLocalRandom.current().nextDouble();

        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);

        return super.getPosition().add(new Vector(x, y, 0));

    }

}
