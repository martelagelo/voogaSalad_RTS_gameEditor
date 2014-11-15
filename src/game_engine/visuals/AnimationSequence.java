package game_engine.visuals;

/**
 * A basic animation. Keeps track of progress, the frame bounds, and the animation that will be
 * played after it
 *
 * @author Zach
 *
 */
public class AnimationSequence implements Updatable {

    private int myStartFrame;
    private int myStopFrame;
    private int myCurrentFrame;
    private String myName;
    private AnimationSequence myNextAnimation;

    /**
     * Initialize the animation
     *
     * @param name the animation's name
     * @param startFrame the start frame of the animation
     * @param stopFrame the stop frame of the animation
     */
    public AnimationSequence (String name, int startFrame, int stopFrame, boolean repeats) {
        myStartFrame = startFrame;
        myStopFrame = stopFrame;
        myName = name;
        myNextAnimation = repeats ? this : new NullAnimation();
    }

    /**
     * Initialize the animation and set the next animation
     *
     * @param nextAnimation
     */
    public AnimationSequence (String name,
                              int startFrame,
                              int stopFrame,
                              AnimationSequence nextAnimation) {
        this(name, startFrame, stopFrame, false);
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
     * Get the next animation, resetting it to ensure it starts from the beginning
     *
     * @return
     */
    public AnimationSequence getNextAnimation () {
        return myNextAnimation.reset();
    }

    /**
     * Increments the animation frame. Return true if the animation is complete.
     */
    @Override
    public boolean update () {
        if (myCurrentFrame < myStopFrame) {
            myCurrentFrame++;
            return false;
        }
        return true;
    }

    /**
     * Get the current frame of the animation
     *
     * @return
     */
    public int getFrame () {
        return myCurrentFrame;
    }

    @Override
    public String toString () {
        return myName;
    }
}
