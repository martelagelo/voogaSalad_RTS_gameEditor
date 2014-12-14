package engine.visuals;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The class for creating the rectangle that the user draws during a game with
 * their cursor. Extends observable so that other classes that want to be
 * notified of unit selection can see where the box was drawn
 *
 * @author John
 *
 */
public class SelectionBox {
    private Rectangle myRectangle;
    private double myXClickLocation, myYClickLocation;
    private MouseEvent myLastEvent;

    public SelectionBox () {
        myRectangle = new Rectangle();
        myRectangle.setStroke(Color.RED);
        myRectangle.setStrokeWidth(2);
        myRectangle.setFill(Color.TRANSPARENT);
    }

    public Rectangle getBox () {
        return myRectangle;
    }

    private void resetBox () {
        myRectangle.setVisible(false);
        setSize(0, 0);
        setLocation(0, 0);
    }

    private void setSize (double width, double height) {
        myRectangle.setWidth(width);
        myRectangle.setHeight(height);
    }

    private void setX (double x) {
        myRectangle.setX(x);
    }

    private void setY (double y) {
        myRectangle.setY(y);
    }

    private void setLocation (double x, double y) {
        setX(x);
        setY(y);
    }

    private void setVisible (boolean b) {
        myRectangle.setVisible(b);
    }

    public double[] clickReleased (MouseEvent e, double mapTranslateX, double mapTranslateY) {
        setVisible(false);

        double xTop = myRectangle.getX() + mapTranslateX;
        double yTop = myRectangle.getY() + mapTranslateY;

        return new double[] { xTop, yTop, xTop + myRectangle.getWidth(),
                yTop + myRectangle.getHeight() };
    }

    public void primaryClick (MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            resetBox();
            myXClickLocation = e.getX();
            myYClickLocation = e.getY();
            setLocation(e.getX(), e.getY());
        }
    }

    public void reactToDrag (MouseEvent e) {
        myLastEvent = e;
        if (myLastEvent.isPrimaryButtonDown()) {
            setVisible(true);
            double newX = myLastEvent.getX();
            double newY = myLastEvent.getY();
            double difX = newX - myXClickLocation;
            double difY = newY - myYClickLocation;

            setSize(Math.abs(difX), Math.abs(difY));
            if (difX <= 0) {
                setX(newX);
            }
            if (difY <= 0) {
                setY(newY);
            }

        }
    }
}
