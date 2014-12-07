package engine.visuals;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import util.SaveLoadUtility;
import util.exceptions.SaveLoadException;


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

    // private double myWidth, myHeight;

    ScrollablePane myPane;

    /**
     * Create the scrollable background
     * 
     * @param screenWidth
     * @param screenHeight
     * @param xBoundary the maximum X value that can be scrolled to
     * @param yBoundary the maximum Y value that can be scrolled to
     */
    public ScrollableBackground (double xBoundary,
                                 double yBoundary,
                                 ScrollablePane pane,
                                 String backgroundURI)
    {
        this.prefWidthProperty().bind(pane.prefWidthProperty());
        this.prefHeightProperty().bind(pane.prefHeightProperty());
        myPane = pane;
        this.myXBoundary = xBoundary;
        this.myYBoundary = yBoundary;
        this.setMinSize(xBoundary, yBoundary);

        tileBackground(backgroundURI);

        // setStyle("-fx-border-color: blue;"); // used for testing to see the edge of the map

    }

    public void tileBackground (String backgroundURI) {
        Image im = null;
        try {
            im = SaveLoadUtility.loadImage(backgroundURI);
        }
        catch (SaveLoadException e) {
        }
        if (im != null) {
            this.setBackground(new Background(new BackgroundImage(im, null, null, null, null)));
        }
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
     * Remove an object from the scrollablebackground
     * 
     * @param g the object to remove
     */
    public void removeObjects (Group g) {
        this.getChildren().remove(g);
    }

    /**
     * Update the view of the background based on the current scroll speed
     */
    public void update () {
        if ((getTranslateX() >= 0 && myXScrollSpeed < 0) ||
            (-getTranslateX() >= (myXBoundary - this.getPrefWidth()) && myXScrollSpeed > 0))
            setXScrollSpeed(0);
        if ((getTranslateY() >= 0 && myYScrollSpeed < 0) ||
            (-getTranslateY() >= (myYBoundary - this.getPrefHeight()) && myYScrollSpeed > 0))
            setYScrollSpeed(0);
        setTranslateX(getTranslateX() - myXScrollSpeed);
        setTranslateY(getTranslateY() - myYScrollSpeed);
        retractOutOfBoundsScroll();
    }

    /**
     * Undo scrolling if we're going out of bounds
     */
    private void retractOutOfBoundsScroll () {
        if (-getTranslateX() >= myXBoundary - this.getPrefWidth())
            setTranslateX(-(myXBoundary - this.getPrefWidth()));
        if (-getTranslateY() >= myYBoundary - this.getPrefHeight())
            setTranslateY(-(myYBoundary - this.getPrefHeight()));
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
