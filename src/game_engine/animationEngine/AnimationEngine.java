package game_engine.animationEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;


/**
 * An animation engine that allows for the playing and storing of animations using a given
 * spritesheet. This engine assumes all spritesheets are given with a horizontally traversable sheet
 *
 * @author Zach
 *
 */
public class AnimationEngine implements Updatable, Displayable {

    private Point2D myTileSize;
    private Animation myCurrentAnimation;
    private int myNumCols;
    private ImageView myDisplay;
    private Map<String, Animation> myAnimations;

    /**
     * Initialize the animation engine.
     *
     * @param spriteSheet the image containing the spritesheet
     * @param tileSize a point2D containing the width(x) and height(y) of each frame in the
     *        spritesheet
     * @param numCols the number of columns across the spritesheet goes before moving to the next
     *        row
     */
    public AnimationEngine (Image spriteSheet, Point2D tileSize, int numCols) {
        myTileSize = tileSize;
        myNumCols = numCols;
        myAnimations = new HashMap<>();
        myDisplay = new ImageView(spriteSheet);
        myDisplay.setViewport(new Rectangle2D(0, 0, tileSize.getX(), tileSize.getY()));

    }

    /**
     * Initialize the animation engine with a given collection of animations
     */
    public AnimationEngine (Image spriteSheet,
                            Point2D tileSize,
                            int numCols,
                            Collection<Animation> animations) {
        this(spriteSheet, tileSize, numCols);
        animations.forEach(animation -> myAnimations.put(animation.toString(), animation));
    }

    /**
     * Set the current animation. Automatically adds it to the map of animations
     *
     * @param animation the current animation
     * @param repeat a boolean indicating whether the animation should loop infinitely
     * @return true
     */
    public boolean setAnimation (Animation animation, boolean repeat) {
        myAnimations.put(animation.toString(), animation);
        myCurrentAnimation = animation;
        if (repeat) {
            myCurrentAnimation.setNextAnimation(myCurrentAnimation);
        }
        return true;
    }

    /**
     * Set the animation to one given by the string corresponding to the animation's name
     *
     * @return true if animation found in engine. False if not.
     */
    public boolean setAnimation (String animation, boolean repeat) {
        if (myAnimations.containsKey(animation)) {
            setAnimation(myAnimations.get(animation), repeat);
        }
        return false;

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

    public void setSelected () {
        setAnimation("Selected", true);        
    }

    public Bounds getBounds () {
        // TODO Auto-generated method stub
        return null;
    }

    public Polygon getVisionPolygon () {
        // TODO Auto-generated method stub
        return null;
    }

}
