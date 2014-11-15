package game_engine.animationEngine;

/**
 * A basic animation. Keeps track of progress, the frame bounds, and the animation that will be
 * played after it
 *
 * @author Zach
 *
 */
public class Animation implements Updatable {

    private int myStartFrame;
    private int myStopFrame;
    private int myCurrentFrame;
    private String myName;
    private Animation myNextAnimation;

    /**
     * Initialize the animation
     *
     * @param name the animation's name
     * @param startFrame the start frame of the animation
     * @param stopFrame the stop frame of the animation
     */
    public Animation (String name, int startFrame, int stopFrame) {
        myStartFrame = startFrame;
        myStopFrame = stopFrame;
        myName = name;
        myNextAnimation = new NullAnimation();
    }

    /**
     * Initialize the animation and set the next animation
     *
     * @param nextAnimation
     */
    public Animation (String name, int startFrame, int stopFrame, Animation nextAnimation) {
        this(name, startFrame, stopFrame);
        myNextAnimation = nextAnimation;
    }

    /**
     * Reset the animation to the start frame
     */
    public void reset () {
        myCurrentFrame = myStartFrame;
    }

    /**
     * Sets the animation's following animation
     *
     * @param animation
     */
    public void setNextAnimation (Animation animation) {
        myNextAnimation = animation;
    }

    /**
     * Get the next animation
     *
     * @return
     */
    public Animation getNextAnimation () {
        return myNextAnimation;
    }

    /**
     * Incriments the animation frame. Return true if the animation is complete.
     */
    @Override
    public boolean update () {
        if (myCurrentFrame <= myStopFrame) {
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
