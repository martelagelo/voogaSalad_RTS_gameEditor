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
    }

    public void released (double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
        // send this information to the
        points = new double[] { topLeftX, topLeftY, bottomRightX, bottomRightY };
        setChanged();
        notifyObservers();
        System.out.println("SelectionBox: (" + topLeftX + ", " + topLeftY + ") , (" + bottomRightX + ", " +
                           bottomRightY + ")");
    }

    public double[] getPoints () {
        return points;
    }

    public Rectangle getBox () {
        return myRectangle;
    }
    
    public void resetBox(){
        myRectangle.setVisible(true);
        myRectangle.setWidth(0);
        myRectangle.setHeight(0);
        myRectangle.setStroke(Color.RED);
        myRectangle.setStrokeWidth(2);
        myRectangle.setFill(Color.TRANSPARENT);
        myRectangle.setX(0);
        myRectangle.setY(0);
    }

    public void setLocation (double pressedX, double pressedY) {
        myRectangle.setX(pressedX);
        myRectangle.setY(pressedY);        
    }
}
