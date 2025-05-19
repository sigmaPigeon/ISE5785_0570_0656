package renderer;
import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera implements Cloneable
{
    private Point p0;
    private Vector vto,vup, vright;
    private double viewPlaneHeight=0.0,
            viewPlaneWidth=0.0,
            distance=0.0;
    private int nX=1, nY=1;



    public static class Builder{
        final Camera camera;

        public Builder() {
            this.camera = new Camera();
        }

        public Builder(Camera camera) {
            this.camera = camera;
        }

        public Builder setLocation(Point position) {

            this.camera.p0 = position;
            return this;
        }
        public Builder setDirection(Vector vto, Vector vup) {
            this.camera.vto = vto.normalize();
            this.camera.vup = vup.normalize();
            return this;
        }
        public Builder setDirection(Point goal, Vector vup) {
            this.camera.vto = goal.subtract(this.camera.p0).normalize();
            this.camera.vright = this.camera.vto.crossProduct(vup).normalize();
            this.camera.vup = this.camera.vright.crossProduct(this.camera.vto).normalize();
            return this;
        }
        public Builder setDirection(Point goal){
            return setDirection(goal, new Vector(0,1,0));
        }
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            this.camera.viewPlaneWidth = width;
            this.camera.viewPlaneHeight = height;
            return this;
        }
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            this.camera.distance = distance;
            return this;
        }
        public Builder setResolution(int nX, int nY){
            return this;
        }
        public Camera build(){
            if (alignZero(camera.viewPlaneWidth)<=0) {throw new MissingResourceException("Camera is missing required parameters", "renderer", "viewPlaneWidth");}
            if (alignZero(camera.viewPlaneHeight)<=0) {throw new MissingResourceException("Camera is missing required parameters", "renderer", "viewPlaneHeight");}
            if (alignZero(camera.distance)<=0) {throw new MissingResourceException("Camera is missing required parameters", "renderer", "distance");}
            if (camera.vup == null) {throw new MissingResourceException("Camera is missing required parameters", "renderer", "vup");}
            if(camera.vto == null) {throw new MissingResourceException("Camera is missing required parameters", "renderer", "vto");}
            if (camera.p0 ==null){throw new MissingResourceException("Camera is missing required parameters", "renderer", "position");}
            if (!isZero(camera.vup.dotProduct(camera.vto))) {throw new IllegalArgumentException("vup and vto must be orthogonal");}

            camera.vright = camera.vto.crossProduct(camera.vup).normalize();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Cloning not supported", e);
            }
        }
    }



    Camera (){
        this.p0 = new Point(0,0,0);
        this.vto = new Vector(0,0,-1);
        this.vup = new Vector(0,1,0);
        this.vright = new Vector(1,0,0);
    }
    public static Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }
double getVpHeight() {
        return viewPlaneHeight;
    }
    double getVpWidth() {
        return viewPlaneWidth;
    }
    public int getnX() {
        return nX;
    }
    public int getnY() {
        return nY;
    }


}
