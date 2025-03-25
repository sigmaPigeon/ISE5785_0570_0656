package primitives;

public class Ray {
    protected final Point head;
    protected final Vector direction;

    public Ray(Point p, Vector v) {
        head = p;
        direction = v.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Ray tmp)) {
            return false;
        }
        return head.equals(tmp.head)
                && direction.equals(tmp.direction);
    }

    @Override
    public String toString() {
        return "head = " + head.toString()
                + "direction = " + direction.toString();

    }
}