package primitives;

/**
 * this class will represent a point in space and its methods
 */
public class Point {

    protected final Double3 xyz;
    /** Zero point*/
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * Constructor to initialize a point based on 3 values
     * @param x the distance from the first axis (x)
     * @param y the distance from the second axis (y)
     * @param z the distance from the third axis (z)
     */
    public Point(double x,double y,double z) {
        this.xyz = new Double3(x,y,z);
    }

    /**
     * Constructor to initialize a point based on double3 param
     * @param xyz the values for point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * method to compare two points in space (both fields and class type)
     * @param obj an unknown object
     * @return if obj is equal to this point
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        return (obj instanceof Point point)
                && this.xyz.equals(point.xyz);
    }

    /**
     * method to convert point to string
     * @return point in string
     */
    @Override
    public String toString() { return "" + xyz; }

    /**
     * method to add to points together
     * @param vec vector we want to add
     * @return this point + vector
     */
    public Point add(Vector vec){

        return new Point(xyz.add(vec.xyz));
    }

    /**
     * method to subtract one point from another
     * @param point the point we want to subtract
     * @return this point - point
     */
    public Vector subtract(Point point){
        if(this.equals(point)){
            throw new IllegalArgumentException("ERROR:zero vector");
        }
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * method to calculate the distance squared (help method)
     * @param point the point we want to calculate the distance from
     * @return distance between those points squared
     */
    public double distanceSquared(Point point){
        double diffX = xyz.d1() - point.xyz.d1();
        double diffY = xyz.d2() - point.xyz.d2();
        double diffZ = xyz.d3() - point.xyz.d3();
        return diffX*diffX + diffY*diffY + diffZ*diffZ;
    }

    /**
     * method to calculate the distance between 2 points
     * @return the distance between those points
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }
}
