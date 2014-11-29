package game_engine.visuals;

import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.sun.istack.internal.NotNull;


/**
 * An animation player that allows for the playing of animations using a given
 * spritesheet. This utility assumes all spritesheets are given with a
 * horizontally traversable sheet
 *
 * @author Zach
 *
 */
public class AnimationPlayer implements Updatable, Displayable {

    private Dimension myTileSize;
    private AnimationSequence myCurrentAnimation;
    private int myNumCols;
    private ImageView myDisplay;
    private Rectangle2D myImageBounds;

    /**
     * Initialize the player
     *
     * @param spriteSheet
     *        the image containing the spritesheet
     * @param tileSize
     *        a point2D containing the width(x) and height(y) of each frame
     *        in the spritesheet
     * @param numCols
     *        the number of columns across the spritesheet goes before
     *        moving to the next row
     */
    public AnimationPlayer (Image spriteSheet, Dimension tileSize, int numCols) {
        myTileSize = tileSize;
        myNumCols = numCols;
        myDisplay = new ImageView(spriteSheet);
        myDisplay.setViewport(new Rectangle2D(0, 0, tileSize.getWidth(), tileSize.getHeight()));
        myCurrentAnimation = new NullAnimationSequence();
        myImageBounds = getImageBounds(spriteSheet);
    }

    /**
     * Get a rectangular bounds of an image based on its dimensions
     *
     * @param img
     *        the image of interest
     * @return the bounds
     */
    private Rectangle2D getImageBounds (Image img) {
        return new Rectangle2D(0, 0, img.getWidth(), img.getHeight());

    }

    /**
     * Set and play the current animation
     *
     * @param animation
     *        the current animation
     */
    public void setAnimation (AnimationSequence animation) {
        myCurrentAnimation = animation;
    }

    /**
     * Set the animation to be played once the current animation completes
     */
    public void setNextAnimation (@NotNull AnimationSequence animation) {
        myCurrentAnimation.setNextAnimation(animation);
    }

    /**
     * Get the display view for the animation
     */
    @Override
    public Node getNode () {
        return myDisplay;
    }

    /**
     * Update the animation. Increment the frame and move on to the next
     * animation if the current one is finished.
     */
    @Override
    public boolean update () {
        // If the current animation is finished, start the next one
        if (myCurrentAnimation.update()) {
            myCurrentAnimation = myCurrentAnimation.getNextAnimation();

        }
        // Get a viewport and make sure it fits
        Rectangle2D viewport = getViewport(myCurrentAnimation.getFrame());
        if (!myImageBounds.contains(viewport)) return false;
        // Set the display viewport to the new viewport
        myDisplay.setViewport(viewport);
        return true;
    }

    /**
     * Creates and returns a viewport based on a frame number. Assumes
     * horizontal traversal of frames
     */
    private Rectangle2D getViewport (int frameNumber) {
        int colNumber = frameNumber / myNumCols; // TODO changed by John to
                                                 // vertical traversal of
        // frames to match our spritesheets
        int rowNumber = frameNumber % myNumCols;
        return new Rectangle2D(colNumber * myTileSize.getWidth(), rowNumber *
                                                                  myTileSize.getHeight(),
                               myTileSize.getWidth(), myTileSize.getHeight());
    }

    public Dimension getDimension () {
        return myTileSize;
    }
}
