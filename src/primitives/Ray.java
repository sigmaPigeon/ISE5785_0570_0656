package primitives;

public class Ray {
    protected final Point head;
    protected final Vector direction;

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        return (obj instanceof Ray ray)
                && this.head.equals(ray.head)
                && this.direction.equals(ray.direction);
    }

    @Override
    public String toString(){
        return "Ray = head: " + head.toString() + '\''
                + "     direction: " + direction.toString();
    }
}
