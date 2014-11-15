package player;

import java.util.Observable;
import javafx.scene.shape.Rectangle;

/**
 * The class for creating the rectangle that the user draws during a game
 * with their cursor. Extends observable so that other classes that want to 
 * be notified of unit selection can see where the box was drawn
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
        points = new double[]{topLeftX, topLeftY, bottomRightX, bottomRightY};
        notifyObservers();
        System.out.println("(" + topLeftX + ", " + topLeftY + ") , (" + bottomRightX + ", " +
                           bottomRightY + ")");
    }
    
    public double[] getPoints(){
        return points;
    }

    public Rectangle getBox () {
        return myRectangle;
    }
}
