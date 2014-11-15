package player;

import java.util.Observable;
import javafx.scene.shape.Rectangle;


public class SelectionBox extends Observable {

    private Rectangle myRectangle;

    public SelectionBox () {
        myRectangle = new Rectangle();
    }

    public void released (double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
        // send this information to the

        System.out.println("(" + topLeftX + ", " + topLeftY + ") , (" + bottomRightX + ", " +
                           bottomRightY + ")");
    }

    public Rectangle getBox () {
        return myRectangle;
    }

}
