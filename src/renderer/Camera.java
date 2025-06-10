
package renderer;
import primitives.*;
import scene.Scene;

import java.util.MissingResourceException;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * Class representing a camera in 3D space.
 * The camera is defined by its position, orientation vectors, and view plane parameters.
 */
public class Camera implements Cloneable {
    private Point p0; // The position of the camera in 3D space
    private Vector vto, vup, vright; // Orientation vectors: towards, up, and right
    private double viewPlaneHeight = 0.0, // Height of the view plane
            viewPlaneWidth = 0.0, // Width of the view plane
            distance = 0.0; // Distance from the camera to the view plane
    ImageWriter imageWriter; // Image writer for rendering the camera view
    RayTracerBase traceRay; // Ray tracer for rendering the scene
    private int nX = 1, nY = 1; // Resolution of the view plane (number of pixels in X and Y directions)

    /**
     * Builder class for constructing a Camera object step by step.
     */
    public static class Builder {
        final Camera camera; // The camera instance being built

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Constructor for the Builder with an existing Camera instance.
         *
         * @param camera The existing Camera instance
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * Sets the position of the camera.
         *
         * @param position The position of the camera
         * @return The Builder instance
         */
        public Builder setLocation(Point position) {
            this.camera.p0 = position;
            return this;
        }

        /**
         * Sets the direction of the camera using vto and vup vectors.
         *
         * @param vto The "towards" vector
         * @param vup The "up" vector
         * @return The Builder instance
         */
        public Builder setDirection(Vector vto, Vector vup) {
            this.camera.vto = vto.normalize();
            this.camera.vup = vup.normalize();
            return this;
        }

        /**
         * Sets the direction of the camera using a goal point and vup vector.
         *
         * @param goal The goal point the camera is looking at
         * @param vup  The "up" vector
         * @return The Builder instance
         */
        public Builder setDirection(Point goal, Vector vup) {
            this.camera.vto = goal.subtract(this.camera.p0).normalize();
            this.camera.vright = this.camera.vto.crossProduct(vup).normalize();
            this.camera.vup = this.camera.vright.crossProduct(this.camera.vto).normalize();
            return this;
        }

        /**
         * Sets the direction of the camera using a goal point and a default vup vector.
         *
         * @param goal The goal point the camera is looking at
         * @return The Builder instance
         */
        public Builder setDirection(Point goal) {
            return setDirection(goal, new Vector(0, 1, 0));
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  The width of the view plane
         * @param height The height of the view plane
         * @return The Builder instance
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive");
            }
            this.camera.viewPlaneWidth = width;
            this.camera.viewPlaneHeight = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance The distance to the view plane
         * @return The Builder instance
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Sets the resolution of the view plane.
         *
         * @param nX The number of horizontal pixels
         * @param nY The number of vertical pixels
         * @return The Builder instance
         */
        public Builder setResolution(int nX, int nY) {
            if (nX <= 0 || nY <= 0) {
                throw new IllegalArgumentException("Resolution must be positive");
            }
            this.camera.nX = nX;
            this.camera.nY = nY;
            return this;
        }
        public Builder setRayTracer(Scene scene,RayTracerType type ) {
            if(type == RayTracerType.SIMPLE){
                this.camera .traceRay = new SimpleRayTracer(scene);;
            }
            if(type == RayTracerType.GRID){
                this.camera .traceRay = null;;
            }
            return this;
        }

        /**
         * Builds and returns the Camera instance.
         *
         * @return The constructed Camera instance
         */
        public Camera build() {
            if (alignZero(camera.viewPlaneWidth) <= 0) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "viewPlaneWidth");
            }
            if (alignZero(camera.viewPlaneHeight) <= 0) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "viewPlaneHeight");
            }
            if (alignZero(camera.distance) <= 0) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "distance");
            }
            if (camera.vup == null) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "vup");
            }
            if (camera.vto == null) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "vto");
            }
            if (camera.p0 == null) {
                throw new MissingResourceException("Camera is missing required parameters", "renderer", "position");
            }
            if (!isZero(camera.vup.dotProduct(camera.vto))) {
                throw new IllegalArgumentException("vup and vto must be orthogonal");
            }
            if (camera.nX <= 0 || camera.nY <= 0) {
                throw new IllegalArgumentException("Resolution must be positive");
            }

            camera.vright = camera.vto.crossProduct(camera.vup).normalize();
            camera.imageWriter= new ImageWriter(camera.nX, camera.nY);
            if (camera.traceRay == null) {
                setRayTracer(null,RayTracerType.SIMPLE);
            }
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Cloning not supported", e);
            }
        }
    }

    /**
     * Default constructor for the Camera class.
     * Initializes the camera with default position and orientation.
     */
    Camera() {
        this.p0 = new Point(0, 0, 0);
        this.vto = new Vector(0, 0, -1);
        this.vup = new Vector(0, 1, 0);
        this.vright = new Vector(1, 0, 0);
    }

    /**
     * Returns a new Builder instance for constructing a Camera.
     *
     * @return A new Builder instance
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel on the view plane.
     *
     * @param nX The number of horizontal pixels
     * @param nY The number of vertical pixels
     * @param j  The column index of the pixel
     * @param i  The row index of the pixel
     * @return The constructed ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vto.scale(distance));
        Point pIJ = pc;
        double xJ = (j - (nX - 1) / 2.0) * (viewPlaneWidth / nX);
        double yI = -(i - (nY - 1) / 2.0) * (viewPlaneHeight / nY);
        if (!isZero(xJ)) {
            pIJ = pIJ.add(vright.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vup.scale(yI));
        }
        return new Ray(p0, pIJ.subtract(p0).normalize());
    }

    /**
     * Gets the height of the view plane.
     *
     * @return The height of the view plane
     */
    double getVpHeight() {
        return viewPlaneHeight;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return The width of the view plane
     */
    double getVpWidth() {
        return viewPlaneWidth;
    }

    /**
     * Gets the number of horizontal pixels in the view plane.
     *
     * @return The number of horizontal pixels
     */
    public int getnX() {
        return nX;
    }

    /**
     * Gets the number of vertical pixels in the view plane.
     *
     * @return The number of vertical pixels
     */
    public int getnY() {
        return nY;
    }
    /**
     * Gets the position of the camera.
     *
     * @return The position of the camera
     */
    public Camera renderImage() {
        if (traceRay == null) {
            throw new IllegalStateException("Ray tracer is not set");
        }
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                castRay(j, i);
            }
        }
        return this;
    }
    /**
     * Prints a grid on the view plane for debugging purposes.
     *
     * @param interval The interval between grid lines
     * @param color    The color of the grid lines
     * @return The Camera instance for method chaining
     */
    public Camera printGrid(int interval,Color color) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY ; j++) {
                if(i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i,j,color);

            }
        }
        return this;
    }
    /**
     * Writes the rendered image to a file.
     *
     * @param imageName The name of the image file
     * @return The Camera instance for method chaining
     */
    public Camera writeToImage(String imageName) {
        imageWriter.writeToImage(imageName);
        return this;
    }
    void castRay(int x, int y) {
        Ray ray = constructRay(nX, nY, x, y);
        Color color = traceRay.traceRay(ray);
        imageWriter.writePixel(x, y, color);
    }


}