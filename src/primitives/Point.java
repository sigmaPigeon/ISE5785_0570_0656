package primitives;

public class Point {

    protected final Double3 xyz;
    public static final Point ZERO = new Point(Double3.ZERO);

    public Point(double x,double y,double z) {
        this.xyz = new Double3(x,y,z);
    }
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return (obj instanceof Point point)
                && this.xyz.equals(point.xyz);
    }

    @Override
    public String toString() { return "" + xyz; }

    public Point add(Vector vec){ return new Point(xyz.add(vec.xyz)); }

    public Vector subtract(Point point){
        return new Vector(xyz.subtract(point.xyz));
    }

    public double distanceSquared(Point point){
        double diffX = xyz.d1() - point.xyz.d1();
        double diffY = xyz.d2() - point.xyz.d2();
        double diffZ = xyz.d3() - point.xyz.d3();
        return diffX*diffX + diffY*diffY + diffZ*diffZ;
    }
}
