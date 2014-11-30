package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.gameRepresentation.stateRepresentation.AnimatorState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import game_engine.visuals.AnimationSequence;
import game_engine.visuals.NullAnimationSequence;

import java.util.HashMap;
import java.util.Map;


/**
 * This GameElement is drawable but not necessarily selectable - examples
 * include terrain. These elements must have a bounding box.
 *
 * @author Steve, Jonathan, Rahul, Zach
 *
 */

public class DrawableGameElementState extends GameElementState implements Boundable {
    private AnimatorState myAnimatorState;
    private Map<String, AnimationSequence> myAnimations;
    private double[] myBounds;

    /**
     * Create a drawable game element at a given x and y position.
     *
     * @param xPosition the x position of the element
     * @param yPosition the y position of the element
     */
    public DrawableGameElementState (Number xPosition, Number yPosition, AnimatorState animatorState) {
        super();
        myAnimatorState = animatorState;
        myBounds = new double[4]; // Initialize the bounds to an empty array
        myAnimations = new HashMap<>();

        // These positions are stored in a numerical attribute map to allow for
        // easy retrieval of
        // attributes by conditions and actions

        attributes.setNumericalAttribute(StateTags.X_POS_STRING, xPosition);
        attributes.setNumericalAttribute(StateTags.Y_POS_STRING, yPosition);
    }

    /**
     * Add an animation to the DrawableGameElementState's list of possible
     * animations
     *
     * @param animation the animation to add
     */
    public void addAnimation (AnimationSequence animation) {
        myAnimations.put(animation.toString(), animation);
    }

    /**
     * Return the animation sequence with a given name
     *
     * @param animationName the name of the animation sequence
     * @return the animation of interest if it exists or null if it does not
     */
    public AnimationSequence getAnimation (String animationName) {
        if (myAnimations.containsKey(animationName)) { return myAnimations.get(animationName); }
        return new NullAnimationSequence();
    }

    @Override
    public double[] getBounds () {
        return myBounds;
    }

    /**
     * Set the bounds
     *
     * @param bounds the new bounds of the state set in a format that is usable by JavaFx's Polygon
     *        object
     */
    public void setBounds (double[] bounds) {
        myBounds = bounds;
    }

}
