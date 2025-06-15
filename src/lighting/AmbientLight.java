package lighting;


import primitives.Color;

import static primitives.Color.BLACK;

public class AmbientLight extends Light {
    static public final Color NONE = BLACK;

    /**
     * Constructor to initialize the ambient light with intensity and color.
     *
     * @param intensity the intensity of the ambient light
     */
    public AmbientLight(Color intensity) {
        super(intensity);
    }



}
