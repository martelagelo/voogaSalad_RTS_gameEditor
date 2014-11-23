package game_engine.visuals.UI;

import java.util.Observable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * The class for creating the rectangle that the user draws during a game
 * with their cursor. Extends observable so that other classes that want to
 * be notified of unit selection can see where the box was drawn
 * 
 * @author John
 *
 */
public class SelectionBox extends Observable {
    private Rectangle myRectangle;

    double[] points = new double[4];

    public SelectionBox () {
        myRectangle = new Rectangle();
        myRectangle.setStroke(Color.RED);
        myRectangle.setStrokeWidth(2);
        myRectangle.setFill(Color.TRANSPARENT);
    }

    public void released (double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
        points = new double[] { topLeftX, topLeftY, bottomRightX, bottomRightY };
        setChanged();
        notifyObservers();
        System.out.println("SelectionBox: (" + topLeftX + ", " + topLeftY + ") , (" + bottomRightX +
                           ", " +
                           bottomRightY + ")");
    }

    public double[] getPoints () {
        return points;
    }

    public Rectangle getBox () {
        return myRectangle;
    }

    public void resetBox () {
        myRectangle.setVisible(true);
        setSize(0, 0);
        setLocation(0, 0);
    }

    public void setSize (double width, double height) {
        myRectangle.setWidth(width);
        myRectangle.setHeight(height);
    }

    public void setX (double x) {
        myRectangle.setX(x);
    }

    public void setY (double y) {
        myRectangle.setY(y);
    }

    public void setLocation (double x, double y) {
        setX(x);
        setY(y);
    }

    public void setVisible (boolean b) {
        myRectangle.setVisible(b);
    }

}
