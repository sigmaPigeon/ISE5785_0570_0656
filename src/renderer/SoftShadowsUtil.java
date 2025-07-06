package renderer;
import lighting.LightSource;
import primitives.Point;
import primitives.Vector;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating soft shadows by sampling multiple directions from a light source.
 * This class provides methods to generate a list of vectors representing directions from a point
 * to various points on the surface of a light source.
 */
public class SoftShadowsUtil {
    public static List<Vector> generateBeamToArea(LightSource light, Point from, int numSamples) {
        List<Vector> directions = new ArrayList<>();
        // Generate numSamples random directions towards points on the light's surface
        for (int i = 0; i < numSamples; i++) {
            // generate direction to a point on the light's surface
            Point target = light.getRandomPointOnSurface();
            Vector dir = target.subtract(from).normalize();
            directions.add(dir);
        }
        return directions;
    }
}


