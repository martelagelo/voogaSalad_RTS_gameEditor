// This entire file is part of my masterpiece.
// JOHN LORENZ

package engine.visuals;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;


/**
 * A scrollablebackground pane for the game. Allows for setting the scrolling speed externally, and
 * internally ensures that the scrolling does not go outside set boundaries
 *
 * @author John
 *
 */
public class ScrollableBackground extends Pane {
    private double myXScrollSpeed, myYScrollSpeed;
    private double myXBoundary, myYBoundary;

    /**
     * Instantiates the background pane. Binds the display size to the double properties passed in
     * to allow for resizing
     * 
     * @param xMapSize the desired total map width
     * @param yMapSize the desired total map height
     * @param displayWidth the property to bind to for display width. When this property is resized,
     *        scrolling will adjust
     * @param displayHeight the property to bind to for display height. When this property is
     *        resized, scrolling will adjust
     * @param background the background image to tile. If null, does not tile.
     */
    public ScrollableBackground (double xMapSize,
                                 double yMapSize,
                                 ReadOnlyDoubleProperty displayWidth,
                                 ReadOnlyDoubleProperty displayHeight,
                                 Image background) {
        prefWidthProperty().bind(displayWidth);
        prefHeightProperty().bind(displayHeight);
        myXBoundary = xMapSize;
        myYBoundary = yMapSize;
        setMinSize(xMapSize, yMapSize);

        tileBackground(background);
    }

    /**
     * Tiles the background a particular image
     * 
     * @param background the image to tile. If the image is null, does not tile.
     */
    public void tileBackground (Image background) {
        if (background == null) return;
        setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
    }

    /**
     * Add objects to be displayed in the scrollablebackground
     *
     * @param g
     *        a group of the objects to add
     */
    public void addObjects (Group g) {
        getChildren().add(g);
    }

    /**
     * Remove an object from the scrollablebackground
     *
     * @param g
     *        the object to remove
     */
    public void removeObjects (Group g) {
        getChildren().remove(g);
    }

    /**
     * Update the view of the background based on the current scroll speed, should be called every
     * frame of the game loop to ensure proper scrolling
     */
    public void update () {
        // these two if checks are very similar, but JavaFX does not provide a separate way to get
        // the x and y translates simultaneously to extract into a method
        if ((getTranslateX() >= 0 && myXScrollSpeed < 0)
            || (-getTranslateX() >= (myXBoundary - getPrefWidth()) && myXScrollSpeed > 0)) {
            setXScrollSpeed(0);
        }
        if ((getTranslateY() >= 0 && myYScrollSpeed < 0)
            || (-getTranslateY() >= (myYBoundary - getPrefHeight()) && myYScrollSpeed > 0)) {
            setYScrollSpeed(0);
        }
        setTranslateX(getTranslateX() - myXScrollSpeed);
        setTranslateY(getTranslateY() - myYScrollSpeed);
        retractOutOfBoundsScroll();
    }

    /**
     * Undo scrolling if we're going out of bounds
     */
    private void retractOutOfBoundsScroll () {
        if (-getTranslateX() >= myXBoundary - getPrefWidth()) {
            setTranslateX(-(myXBoundary - getPrefWidth()));
        }
        if (-getTranslateY() >= myYBoundary - getPrefHeight()) {
            setTranslateY(-(myYBoundary - getPrefHeight()));
        }
        if (getTranslateX() >= 0) {
            setTranslateX(0);
        }
        if (getTranslateY() >= 0) {
            setTranslateY(0);
        }
    }

    /**
     * Sets the scrolling speed for the background in pixels/frame. Will internally change this
     * speed if boundaries are overstepped
     * 
     * @param speed pixels/ frame horizontal speed. Negative scrolls left, positive scrolls right,
     *        zero is stationary
     */
    public void setXScrollSpeed (double speed) {
        myXScrollSpeed = speed;
    }

    /**
     * Sets the scrolling speed for the background in pixels/frame. Will internally change this
     * speed if boundaries are overstepped
     * 
     * @param speed pixels/ frame vertical speed. Negative scrolls down, positive scrolls up, zero
     *        is stationary
     */
    public void setYScrollSpeed (double speed) {
        myYScrollSpeed = speed;
    }

}
