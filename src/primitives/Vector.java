package primitives;

public class Vector extends Point {

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
    public String toString(){
        return "Vector = " + xyz.toString();
    }

    public double lengthSquared(){
        return super.distanceSquared(ZERO);
    }
}
