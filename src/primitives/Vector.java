package primitives;

public class Vector extends Point {

    public Vector(double x,double y ,double z) {
        super(x,y,z);
        if(equals(ZERO))  
            throw new IllegalArgumentException("zero vector");
    }

    public Vector(Double3 xyz) {
        super(xyz);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        return (obj instanceof Vector vec)
                && this.xyz.equals(vec.xyz);
    }

    @Override
    public String toString() { return "->" + super.toString(); }

    public Vector add(Vector v){
        return new Vector(xyz.add(v.xyz));
    }

    public Vector scale(double scalar){ return new Vector(xyz.scale(scalar));}

    public double dotProduct(Vector v){
        return xyz.d1()*v.xyz.d1()
                +xyz.d2()*v.xyz.d2()
                + xyz.d3()*v.xyz.d3();
    }

    public Vector crossProduct(Vector v){
        double a1=xyz.d1();
        double a2=xyz.d2();
        double a3=xyz.d3();
        double b1=v.xyz.d1();
        double b2=v.xyz.d2();
        double b3=xyz.d3();
        return new Vector(a2*b3-a3*b2,a3*b1-a1*b3,a1*b2-a2*b1);
    }

    public double lengthSquared(){
        return dotProduct(this);
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        return scale(1/length());
    }

}
