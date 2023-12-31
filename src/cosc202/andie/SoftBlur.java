package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Soft blur filter.
 * </p>
 * 
 * <p>
 * A Soft blur filter blurs an image by applying a set Kernel to an image, and can be implemented by a convoloution.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Tristan Kitto
 * @version 1.0
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Construct a Soft blur filter.
     * </p>
     * 
     */
    SoftBlur() {

    }

    /**
     * <p>
     * Apply a Soft blur filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Soft blur filter is implemented via convolution.
     * </p>
     * 
     * @param input The image to apply the Soft blur filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {

        int radius = 1;

        // The values for the kernel as a 9-element array
        float[] array = { 0, 1 / 8.0f, 0,
                1 / 8.0f, 1 / 2.0f, 1 / 8.0f,
                0, 1 / 8.0f, 0 };
        // Make a 3x3 filter from the array
        Kernel kernel = new Kernel(3, 3, array);

        // Apply the kernel with border padding
        BufferedImage paddedInput = new BufferedImage(input.getWidth() + 2 * radius, input.getHeight() + 2 * radius,
                input.getType());
        for (int y = 0; y < input.getHeight(); y++) {
            for (int x = 0; x < input.getWidth(); x++) {
                paddedInput.setRGB(x + radius, y + radius, input.getRGB(x, y));
            }
        }

        // Apply this as a convolution - same code as in MeanFilter
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(paddedInput, output);
        return output;
    }
}