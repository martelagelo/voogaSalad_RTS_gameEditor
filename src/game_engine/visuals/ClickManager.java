package game_engine.visuals;

import java.util.Observable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;


/**
 * 
 * The class for managing the clicks the user makes. Takes in click
 * information and allows observers to get that information.
 * 
 * @author John
 *
 */
public class ClickManager extends Observable {

    boolean isPrimary;
    boolean isSecondary;
    double xLoc, yLoc;

    public ClickManager () {
    }

    public void clicked (MouseEvent event, double xLoc, double yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        if (event.isPrimaryButtonDown()) {
            isPrimary = true;
        }
        else if (event.isSecondaryButtonDown()) {
            isPrimary = false;
        }

        isSecondary = !isPrimary;

        setChanged();
        notifyObservers();
    }

    public Point2D getLoc () {
        return new Point2D(xLoc, yLoc);
    }

    public boolean isPrimary () {
        return isPrimary;
    }

    public boolean isSecondary () {
        return isSecondary;
    }
}
