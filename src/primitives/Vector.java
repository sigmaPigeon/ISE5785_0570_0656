package primitives;

/**
 * class to represent a vector in the space
 */
public class Vector extends Point {
    /**
     * Constructor to initialize the vector based on 3 values
     * @param x the distance from the first axis (x)
     * @param y the distance from the second axis (y)
     * @param z the distance from the third axis (z)
     */
    public Vector(double x,double y ,double z) {
        super(x,y,z);
        if(super.equals(ZERO))
            throw new IllegalArgumentException("ERROR:zero vector");
    }

    /**
     * Constructor to initialize a vector based on double3 param
     * @param xyz the values for the vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(equals(ZERO)){
            throw new IllegalArgumentException("ERROR:zero vector");
        }
    }
    /**
     * method to compare two vectors in space (both fields and class type)
     * @param obj an unknown object
     * @return if obj is equal to this point
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        return (obj instanceof Vector vec)
                && this.xyz.equals(vec.xyz);
    }
    /**
     * method to convert point to string
     * @return point in string
     */
    @Override
    public String toString() { return "->" + super.toString(); }
    /**
     * method to add to points together
     * @param v vector we want to add
     * @return this point + vector
     */
    public Vector add(Vector v){
        if(xyz.equals(v.scale(-1).xyz)){
            throw new IllegalArgumentException("ERROR: zero vector");
        }
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * method to multiply a vector by a scalar
     * @param scalar the number we want to multiply with
     * @return vector * scalar
     */
    public Vector scale(double scalar){ return new Vector(xyz.scale(scalar));}

    /**
     * method to calculate the dot product of 2 vectors
     * @param v the vector we want to calculate the dot product with
     * @return the dot product of those 2 vectors
     */
    public double dotProduct(Vector v){
        return xyz.d1()*v.xyz.d1()
                +xyz.d2()*v.xyz.d2()
                + xyz.d3()*v.xyz.d3();
    }

    /**
     * method to calculate the cross product of 2 vectors
     * @param v the second vector
     * @return the cross product
     */
    public Vector crossProduct(Vector v){
        double a1=xyz.d1(),a2=xyz.d2(),a3=xyz.d3();
        double b1=v.xyz.d1(),b2=v.xyz.d2(),b3=v.xyz.d3();

        double c1=a2*b3-a3*b2,c2=a3*b1-a1*b3,c3=a1*b2-a2*b1;
        if(Util.isZero(c1*c1+c2*c2+c3*c3)){
            throw new IllegalArgumentException("ERROR:parallel vectors");
        }
        return new Vector(c1,c2,c3);
    }
    /**
     * method to calculate the length of the vector squared
     * @return length of the vector squared
     */
    public double lengthSquared(){
        return dotProduct(this);
    }

    /**
     * method to calculate the length of a vector
     * @return length of this vector
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * method to calculate the normal vector of this vector
     * @return this vector normalized
     */
    public Vector normalize(){
        return scale(1/length());
    }

}
