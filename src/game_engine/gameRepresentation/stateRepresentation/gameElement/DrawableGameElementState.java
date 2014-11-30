package game_engine.gameRepresentation.stateRepresentation.gameElement;

import javafx.scene.Node;
import game_engine.gameRepresentation.stateRepresentation.AnimationTag;
import game_engine.gameRepresentation.stateRepresentation.AnimatorState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import game_engine.visuals.Displayable;
import game_engine.visuals.elementVisuals.animations.AnimationSequence;


/**
 * This GameElement is drawable but not necessarily selectable - examples
 * include terrain. These elements must have a bounding box.
 *
 * @author Steve, Jonathan, Rahul, Zach
 *
 */

public class DrawableGameElementState extends GameElementState implements Boundable, Displayable {
    
    private AnimatorState myAnimatorState;
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
        
        //TODO find better fixes?
        myBounds = new double[8]; // Initialize the bounds to an empty array

        // These positions are stored in a numerical attribute map to allow for
        // easy retrieval of
        // attributes by conditions and actions

        attributes.setNumericalAttribute(StateTags.X_POS_STRING, xPosition);
        attributes.setNumericalAttribute(StateTags.Y_POS_STRING, yPosition);
    }

    
    // TODO : why do the next two methods exist?
    /**
     * Add an animation to the DrawableGameElementState's list of possible
     * animations
     *
     * @param animation the animation to add
     */
    public void addAnimation (AnimationSequence animation) {
        myAnimatorState.addAnimationSequence(animation.getMyName(), animation);
    }

    /**
     * Return the animation sequence with a given name
     *
     * @param animationName the name of the animation sequence
     * @return the animation of interest if it exists or null if it does not
     */
    public AnimationSequence getAnimation (AnimationTag tag) {
        return myAnimatorState.getAnimationSequence(tag);
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


    @Override
    public Node getNode () {
        // TODO Auto-generated method stub
        return null;
    }

}
