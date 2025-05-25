package scene;

import geometries.Geometries;
import geometries.Geometry;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color backgroundColor = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(AmbientLight.NONE);
    public Geometries geometries = new Geometries();

    /**
     * Constructor to initialize the scene with a name.
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }
    /**
     * setter method to initialize the background color of the scene
     * @param color the background color of the scene
     */
    public void setBackgroundColor(Color color){
        this.backgroundColor = color;
    }

    /**
     * setter method to initialize the intensity
     * @param intensity the intensity of the ambientlight
     */
    public void setAmbientLight(Color intensity) {
        this.ambientLight = new AmbientLight(intensity);
    }

    /**
     * setter method to initialize the geometries there are on the scene
     * @param geometries the geometries of the scene
     */
    public void setGeometries(Geometries geometries) {
        this.geometries = geometries;
    }
}


