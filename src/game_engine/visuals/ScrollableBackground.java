package game_engine.visuals;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;


/**
 * A scrollablebackground pane for the game
 * 
 * @author John
 *
 */
public class ScrollableBackground extends Pane
{
    private double myXScrollSpeed, myYScrollSpeed;
    private double myXBoundary, myYBoundary;

    /**
     * Create the scrollable background
     * 
     * @param screenWidth
     * @param screenHeight
     * @param xBoundary the maximum X value that can be scrolled to
     * @param yBoundary the maximum Y value that can be scrolled to
     */
    public ScrollableBackground (double screenWidth,
                                 double screenHeight,
                                 double xBoundary,
                                 double yBoundary)
    {
        this.myXBoundary = xBoundary;
        this.myYBoundary = yBoundary;
        
        this.setMinSize(xBoundary, yBoundary);

        setStyle("-fx-border-color: blue;"); // used for testing to see the edge of the map

    }

    /**
     * Add objects to be displayed in the scrollablebackground
     * 
     * @param g a group of the objects to add
     */
    public void addObjects (Group g) {
        this.getChildren().add(g);
    }

    /**
     * Update the view of the background based on the current scroll speed
     */
    public void update (double screenWidth, double screenHeight) {
        if ((getTranslateX() >= 0 && myXScrollSpeed < 0) ||
            (-getTranslateX() >= (myXBoundary - screenWidth) && myXScrollSpeed > 0))
            setXScrollSpeed(0);
        if ((getTranslateY() >= 0 && myYScrollSpeed < 0) ||
            (-getTranslateY() >= (myYBoundary - screenHeight) && myYScrollSpeed > 0))
            setYScrollSpeed(0);
        setTranslateX(getTranslateX() - myXScrollSpeed);
        setTranslateY(getTranslateY() - myYScrollSpeed);
        retractOutOfBoundsScroll(screenWidth, screenHeight);
    }

    /**
     * Undo scrolling if we're going out of bounds
     */
    private void retractOutOfBoundsScroll (double screenWidth, double screenHeight) {
        if (-getTranslateX() >= myXBoundary - screenWidth) setTranslateX(-(myXBoundary - screenWidth));
        if (-getTranslateY() >= myYBoundary - screenHeight) setTranslateY(-(myYBoundary - screenHeight));
        if (getTranslateX() >= 0) setTranslateX(0);
        if (getTranslateY() >= 0) setTranslateY(0);
    }

    public void setXScrollSpeed (double speed) {
        myXScrollSpeed = speed;
    }

    public void setYScrollSpeed (double speed) {
        myYScrollSpeed = speed;
    }

}
