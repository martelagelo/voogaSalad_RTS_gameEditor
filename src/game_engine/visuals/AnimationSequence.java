package game_engine.visuals;

import game_engine.gameRepresentation.stateRepresentation.AnimationTag;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;

/**
 * A basic animation. Keeps track of progress, the frame bounds, and the
 * animation that will be played after it
 *
 * @author Zachary Bears, Rahul Harikrishnan
 *
 */
public class AnimationSequence implements Updatable {

    private int myStartFrame;
    private int myStopFrame;
    private int myCurrentFrame;
    private AnimationTag myName;
    private AnimationSequence myNextAnimation;
    private double mySlownessMultiplier;
    private int myFrameCounter;

    /**
     * Initialize the animation
     *
     * @param name
     *            the animation's name
     * @param startFrame
     *            the start frame of the animation
     * @param stopFrame
     *            the stop frame of the animation
     */
    public AnimationSequence (AnimationTag name, int startFrame, int stopFrame) {
        this(name, startFrame, stopFrame, new NullAnimationSequence());
        mySlownessMultiplier = 1; // TODO: wtf is this

    }

    /**
     * Initialize the animation and insert a multiplier for slowness
     *
     * @param slownessMultiplier
     *            a multiplier for the speed of the animation. Must be less than
     *            1.
     */
    public AnimationSequence (AnimationTag name, int startFrame, int stopFrame, boolean repeats,
            double slownessMultiplier) {
        this(name, startFrame, stopFrame);
        if (slownessMultiplier < 1) {
            mySlownessMultiplier = slownessMultiplier;
        }
    }

    /**
     * Initialize the animation and set the next animation
     *
     * @param nextAnimation
     */
    public AnimationSequence (AnimationTag name, int startFrame, int stopFrame,
            AnimationSequence nextAnimation) {
        myStartFrame = startFrame;
        myCurrentFrame = startFrame;
        myStopFrame = stopFrame;
        myName = name;
        mySlownessMultiplier = 1;
        myNextAnimation = nextAnimation;
    }

    /**
     * Reset the animation to the start frame
     */
    public AnimationSequence reset () {
        myCurrentFrame = myStartFrame;
        return this;
    }

    /**
     * Sets the animation's following animation
     *
     * @param animation
     */
    public void setNextAnimation (AnimationSequence animation) {
        myNextAnimation = animation;
    }

    /**
     * Get the next animation, resetting it to ensure it starts from the
     * beginning
     *
     * @return
     */
    public AnimationSequence getNextAnimation () {
        return (myNextAnimation == null) ? this.reset() : myNextAnimation.reset();

    }

    /**
     * Increments the animation frame. Return true if the animation is complete.
     */
    @Override
    public boolean update () {
        if (myFrameCounter < 1 / mySlownessMultiplier) {
            myFrameCounter++;
        } else {
            myFrameCounter = 0;
            if (myCurrentFrame < myStopFrame) {
                myCurrentFrame++;
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Get the current frame of the animation
     *
     * @return
     */
    public int getFrame () {
        return myCurrentFrame;
    }

    //TODO: fix because no longer takes in a String name but an AnimationTag
  /*  @Override
    public String toString () {
        return myName;
    }*/
}
