package cosc202.andie;

import java.awt.*;
import javax.swing.*;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well
 * as zooming
 * in and out.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is
     * zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally
     * as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Set the currently displayed image.
     * </p>
     * 
     * @param image The image to be set as the displayed image.
     */
    public void setImage(EditableImage image) {
        this.image = image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * </p>
     * 
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100 * scale;
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the
     * original size, 50% is half-size, etc.
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * 
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (zoomPercent < 0) {
            zoomPercent = 0;
        }
        if (zoomPercent > 200) {
            zoomPercent = 200;
        }
        scale = zoomPercent / 100;
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a
     * default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (image.hasImage()) {
            return new Dimension((int) Math.round(image.getCurrentImage().getWidth() * scale),
                    (int) Math.round(image.getCurrentImage().getHeight() * scale));
        } else {
            return new Dimension(450, 450);
        }
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();

            if (ViewActions.CropAction.crop) {
                int width = Math.abs(ViewActions.CropAction.endX - ViewActions.CropAction.startX);
                int height = Math.abs(ViewActions.CropAction.endY - ViewActions.CropAction.startY);
                int startX = Math.min(ViewActions.CropAction.startX, ViewActions.CropAction.endX);
                int startY = Math.min(ViewActions.CropAction.startY, ViewActions.CropAction.endY);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 128));
                g2d.fillRect(startX, startY, width, height);
            }
            if (ViewActions.DrawShapesAction.drawShape) {
                int width = Math.abs(ViewActions.DrawShapesAction.endX - ViewActions.DrawShapesAction.startX);
                int height = Math.abs(ViewActions.DrawShapesAction.endY - ViewActions.DrawShapesAction.startY);
                int startX = Math.min(ViewActions.DrawShapesAction.startX, ViewActions.DrawShapesAction.endX);
                int startY = Math.min(ViewActions.DrawShapesAction.startY, ViewActions.DrawShapesAction.endY);
                int endX = ViewActions.DrawShapesAction.endX;
                int endY = ViewActions.DrawShapesAction.endY;
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(ViewActions.DrawShapesAction.colour);
                g2d.setStroke(ViewActions.DrawShapesAction.strokeSize);
                switch (ViewActions.DrawShapesAction.shape) {
                    case "Rectangle":
                        g2d.drawRect(startX, startY, width, height);
                        break;
                    case "filledRectangle":
                        g2d.fillRect(startX, startY, width, height);
                        break;
                    case "Oval":
                        g2d.drawOval(startX, startY, width, height);
                        break;
                    case "filledOval":
                        g2d.fillOval(startX, startY, width, height);
                        break;
                    case "Line":
                        g2d.drawLine(ViewActions.DrawShapesAction.startX,
                                ViewActions.DrawShapesAction.startY, endX, endY);
                        break;
                }
            }

            if (ViewActions.DrawOvalAction.drawOval) {
                int width = Math.abs(ViewActions.DrawOvalAction.endX - ViewActions.DrawOvalAction.startX);
                int height = Math.abs(ViewActions.DrawOvalAction.endY - ViewActions.DrawOvalAction.startY);
                int startX = Math.min(ViewActions.DrawOvalAction.startX, ViewActions.DrawOvalAction.endX);
                int startY = Math.min(ViewActions.DrawOvalAction.startY, ViewActions.DrawOvalAction.endY);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 128));
                g2d.drawOval(startX, startY, width, height);
            }

            if (ViewActions.DrawLineAction.drawLine) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 128));
                g2d.drawLine(ViewActions.DrawLineAction.startX, ViewActions.DrawLineAction.startY, ViewActions.DrawLineAction.endX, ViewActions.DrawLineAction.endY);
            }

            if (EditActions.TextAction.text) {
                int width = Math.abs(EditActions.TextAction.endX - EditActions.TextAction.startX);
                int height = Math.abs(EditActions.TextAction.endY - EditActions.TextAction.startY);
                int startX = Math.min(EditActions.TextAction.startX, EditActions.TextAction.endX);
                int startY = Math.min(EditActions.TextAction.startY, EditActions.TextAction.endY);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 128));
                g2d.fillRect(startX, startY, width, height);
            }
        }

    }

}
