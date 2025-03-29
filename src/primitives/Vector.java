package primitives;

public class Vector extends Point {

    public Vector(double x,double y ,double z) {
        super(x,y,z);
        if(super.equals(ZERO))
            throw new IllegalArgumentException("ERROR:zero vector");
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if(equals(ZERO)){
            throw new IllegalArgumentException("ERROR:zero vector");
        }
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
        if(xyz.equals(v.scale(-1).xyz)){
            throw new IllegalArgumentException("ERROR: zero vector");
        }
        return new Vector(xyz.add(v.xyz));
    }

    public Vector scale(double scalar){ return new Vector(xyz.scale(scalar));}

    public double dotProduct(Vector v){
        return xyz.d1()*v.xyz.d1()
                +xyz.d2()*v.xyz.d2()
                + xyz.d3()*v.xyz.d3();
    }

    public Vector crossProduct(Vector v){
        double a1=xyz.d1(),a2=xyz.d2(),a3=xyz.d3();
        double b1=v.xyz.d1(),b2=v.xyz.d2(),b3=v.xyz.d3();

        double c1=a2*b3-a3*b2,c2=a3*b1-a1*b3,c3=a1*b2-a2*b1;
        if(Util.isZero(c1*c1+c2*c2+c3*c3)){
            throw new IllegalArgumentException("ERROR:parallel vectors");
        }
        return new Vector(c1,c2,c3);
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
