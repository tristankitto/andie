package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to draw an Oval on an image in a selected region.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Joshua Bodeker
 * @version 1.0
 */
public class DrawOval implements ImageOperation, java.io.Serializable {

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    /**
     * <p>
     * Create a new draw Oval operation.
     * </p>
     * 
     * @param startX Starting pixel for the crop on the x axis
     * @param startY Starting pixel for the crop on the y axis
     * @param endX   Ending pixel for the crop on the x axis
     * @param endY   Ending pixel for the crop on the y axis
     */
    DrawOval(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * <p>
     * Draw a Oval on an image.
     * </p>
     * 
     * <p>
     * The draw Oval operation takes a starting point and ending point on an
     * image and draws a Oval on the image corresponding to that start and end
     * point.
     * </p>
     * 
     * @param input The image to draw on.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input) {
        Graphics2D g2d = input.createGraphics();

        if (startX > endX && startY > endY) {
            g2d.drawOval(endX + 10, endY + 10, startX - endX, startY - endY);
        }
        if (startX < endX && startY > endY) {
            g2d.drawOval(startX + 10, endY + 10, endX - startX, startY - endY);
        }
        if (startX > endX && startY < endY) {
            g2d.drawOval(endX + 10, startY + 10, startX - endX, endY - startY);
        }
        g2d.drawOval(startX + 10, startY + 10, endX - startX, endY - startY);
        return input;
    }
}