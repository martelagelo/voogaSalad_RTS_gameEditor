package game_engine.visuals;

import javafx.scene.Group;
import javafx.scene.layout.Pane;


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

    private double myWidth, myHeight;

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
        this.myWidth = screenWidth;
        this.myHeight = screenHeight;
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
     * Update the view ofthe background based on the current scroll speed
     */
    public void update () {
        if ((getTranslateX() >= 0 && myXScrollSpeed < 0) ||
            (-getTranslateX() >= (myXBoundary - myWidth) && myXScrollSpeed > 0))
            setXScrollSpeed(0);
        if ((getTranslateY() >= 0 && myYScrollSpeed < 0) ||
            (-getTranslateY() >= (myYBoundary - myHeight) && myYScrollSpeed > 0))
            setYScrollSpeed(0);
        setTranslateX(getTranslateX() - myXScrollSpeed);
        setTranslateY(getTranslateY() - myYScrollSpeed);
        retractOutOfBoundsScroll();
    }

    /**
     * Undo scrolling if we're going out of bounds
     */
    private void retractOutOfBoundsScroll () {
        if (-getTranslateX() >= myXBoundary - myWidth) setTranslateX(-(myXBoundary - myWidth));
        if (-getTranslateY() >= myYBoundary - myHeight) setTranslateY(-(myYBoundary - myHeight));
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
