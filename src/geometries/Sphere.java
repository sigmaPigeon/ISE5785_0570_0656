package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    private final Point center;

    public Sphere(Point point, double radius) {
        super(radius);
        this.center = point;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
