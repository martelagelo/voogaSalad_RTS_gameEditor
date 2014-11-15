package game_engine.visuals;

import java.util.Collection;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * An animation player that allows for the playing of animations using a given
 * spritesheet. This player assumes all spritesheets are given with a horizontally traversable sheet
 *
 * @author Zach
 *
 */
public class AnimationPlayer implements Updatable, Displayable {

    private Point2D myTileSize;
    private Animation myCurrentAnimation;
    private int myNumCols;
    private ImageView myDisplay;

    /**
     * Initialize the player
     *
     * @param spriteSheet the image containing the spritesheet
     * @param tileSize a point2D containing the width(x) and height(y) of each frame in the
     *        spritesheet
     * @param numCols the number of columns across the spritesheet goes before moving to the next
     *        row
     */
    public AnimationPlayer (Image spriteSheet, Point2D tileSize, int numCols) {
        myTileSize = tileSize;
        myNumCols = numCols;
        myDisplay = new ImageView(spriteSheet);
        myDisplay.setViewport(new Rectangle2D(0, 0, tileSize.getX(), tileSize.getY()));

    }

    /**
     * Initialize the animation engine with a given collection of animations
     */
    public AnimationPlayer (Image spriteSheet,
                            Point2D tileSize,
                            int numCols,
                            Collection<Animation> animations) {
        this(spriteSheet, tileSize, numCols);
    }

    /**
     * Set and play the current animation
     *
     * @param animation the current animation
     * @param repeat a boolean indicating whether the animation should loop infinitely
     * @return true
     */
    public boolean setAnimation (Animation animation, boolean repeat) {
        myCurrentAnimation = animation;
        if (repeat) {
            myCurrentAnimation.setNextAnimation(myCurrentAnimation);
        }
        return true;
    }

    /**
     * Get the display view for the animation
     */
    @Override
    public Node getNode () {
        return myDisplay;
    }

    /**
     * Update the animation. Increment the frame and move on to the next animation if the current
     * one is finished.
     */
    @Override
    public boolean update () {
        // If the current animation is finished, start the next one
        if (myCurrentAnimation.update()) {
            myCurrentAnimation = myCurrentAnimation.getNextAnimation();
        }
        // Get a viewport and make sure it fits
        Rectangle2D viewport = getViewport(myCurrentAnimation.getFrame());
        if (!myDisplay.getViewport().contains(viewport))
            return false;
        // Set the display viewport to the new viewport
        myDisplay.setViewport(viewport);
        return true;
    }

    /**
     * Creates and returns a viewport based on a frame number. Assumes horizontal traversal of
     * frames
     */
    private Rectangle2D getViewport (int frameNumber) {
        int colNumber = frameNumber % myNumCols;
        int rowNumber = frameNumber / myNumCols;
        return new Rectangle2D(colNumber * myTileSize.getX(), rowNumber * myTileSize.getY(),
                               (colNumber + 1) * myTileSize.getX(), (rowNumber + 1) *
                                                                    myTileSize.getY());
    }
}
