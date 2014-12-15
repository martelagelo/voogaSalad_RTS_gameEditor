// This entire file is part of my masterpiece.
// JOHN LORENZ

package engine.visuals;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 * Class that allows for drawing any shape and being able to notify what was drawn
 *
 * @author John
 *
 */
public class SelectionShape {
    private Shape myShape;
    private double myXClickLocation, myYClickLocation;
    private double myXOffset;
    private double myYOffset;

    /**
     * Creates a new shape that contains an internal offset and methods for resizing
     * 
     * @param shape the JavaFX shape that you want to draw
     * @param offsetX an internal offset for setting the local coordinate frame
     * @param offsetY an internal offset for setting the local coordinate frame
     * @param penColor the color of the border
     * @param penWidth the width of the border
     */
    public SelectionShape (Shape shape,
                           double offsetX,
                           double offsetY,
                           Color penColor,
                           double penWidth) {
        myShape = shape;
        myShape.setStroke(penColor);
        myShape.setStrokeWidth(penWidth);
        myShape.setFill(Color.TRANSPARENT);
        myXOffset = offsetX;
        myYOffset = offsetY;
    }

    /**
     * returns the javafx Node for displaying
     * 
     * @return
     */
    public Shape getNode () {
        return myShape;
    }

    /**
     * Scales the X and Y dimensions of the shape to account for dragging
     * 
     * @param x
     * @param y
     */
    private void setSize (double x, double y) {
        myShape.setScaleX(x);
        myShape.setScaleY(y);
    }

    /**
     * Sets the translate properties of the shape
     * 
     * @param x
     * @param y
     */
    private void setLocation (double x, double y) {
        myShape.setTranslateX(x);
        myShape.setTranslateY(y);
    }

    /**
     * used to notify the shape that the drawing is finished
     */
    public void completeRendering () {
        myShape.setVisible(false);
    }

    /**
     * Resizes the selectionShape based on the point values given in the point2D, by scaling and
     * shifting the shape
     * 
     * @param point
     */
    public void resize (Point2D point) {
        myShape.setVisible(true);
        double difX = point.getX() - myXClickLocation;
        double difY = point.getY() - myYClickLocation;

        setSize(Math.abs(difX), Math.abs(difY));
        if (difX <= 0) {
            setLocation(point.getX(), myShape.getTranslateY());
        }
        if (difY <= 0) {
            setLocation(myShape.getTranslateX(), point.getY());
        }
    }

    /**
     * determines whether a given point is contained relative to the local coordinate frame,
     * specified by this selectionShape's offsets
     * 
     * @param point
     * @return true if the point is contained
     */
    public boolean contains (Point2D point) {
        return myShape.contains(point.getX() + myXOffset, point.getY() + myYOffset);
    }
}
