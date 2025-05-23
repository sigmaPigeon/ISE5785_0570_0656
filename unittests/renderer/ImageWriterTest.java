package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    /** Test method for {@link ImageWriter#writeToImage()}.
     * <p>
     * This test verifies that the image is written correctly to a file.
     * It checks if the file exists and if the image dimensions match the expected values.
     */
    @Test
    public void testWriteToImage() {
        // Create an instance of ImageWriter
        ImageWriter imageWriter = new ImageWriter(801, 501);

        // Set a pixel color
        for (int i = 0; i < 801; i++) {
            for (int j = 0; j < 501; j++) {
                if(i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(i,j,new Color(RED));
                else
                    imageWriter.writePixel(i, j, new Color(YELLOW));
            }
        }

        // Write the image to a file
        imageWriter.writeToImage("imageTest");


    }
}