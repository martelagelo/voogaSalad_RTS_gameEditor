package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Spritesheet;
import java.util.HashMap;
import java.util.Map;


/**
 * This GameElement is drawable but not necessarily selectable - examples include terrain. These
 * elements must have a bounding box.
 * 
 * @author Steve, Jonathan, Rahul
 *
 */

public class DrawableGameElementState extends GameElementState implements Boundable {
    public static final String X_POS_STRING = "xPosition";
    public static final String Y_POS_STRING = "yPosition";

    private Spritesheet mySpritesheet;
    private Map<String, AnimationSequence> myAnimations;
    private AnimationSequence myCurrentAnimation;
    private double[] myBounds;

    /**
     * Create a drawable game element at a given x and y position.
     * 
     * @param xPosition
     * @param yPosition
     */
    public DrawableGameElementState (Number xPosition, Number yPosition) {
        myAnimations = new HashMap<>();
        // These positions are stored in a numerical attribute map to allow for easy retrieval of
        // attributes by conditions and actions
        this.myNumericalAttributes.add(new Attribute<Number>(X_POS_STRING, xPosition));
        this.myNumericalAttributes.add(new Attribute<Number>(Y_POS_STRING, yPosition));
    }

    /**
     * Add an animation to the DrawableGameElementState's list of possible animations
     * 
     * @param animation
     */
    public void addAnimation (AnimationSequence animation) {
        myAnimations.put(animation.toString(), animation);
    }

    /**
     * @return the animation that is currently being displayed by the state
     */
    public AnimationSequence getAnimation () {
        return myCurrentAnimation;
    }

    // TODO remove these methods
    public void setAnimation (String animationName) {
        myCurrentAnimation = myAnimations.get(animationName);
    }

    public void setSpritesheet (Spritesheet spritesheet) {
        mySpritesheet = spritesheet;
        myBounds = new double[4]; // x, y, width, height
        myBounds[0] = getNumericalAttribute(X_POS_STRING).doubleValue();
        myBounds[1] = getNumericalAttribute(Y_POS_STRING).doubleValue();
        myBounds[2] = mySpritesheet.frameDimensions.getWidth();
        myBounds[3] = mySpritesheet.frameDimensions.getHeight();
    }

    public Spritesheet getSpritesheet () {
        return mySpritesheet;
    }

    @Override
    public double[] getBounds () {
        return myBounds;
    }

}
