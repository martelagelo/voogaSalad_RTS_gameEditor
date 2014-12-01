package engine.visual.animation;

import java.io.Serializable;
import java.util.List;
import model.state.AnimationTag;
import model.state.gameelement.traits.Updatable;


/**
 * A basic animation. Keeps track of progress, the frame bounds, and the
 * animation that will be played after it
 *
 * @author Zachary Bears, Rahul Harikrishnan, Steve
 *
 */
public class AnimationSequence implements Updatable, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9193394127044465741L;
    
    private int myStartFrame;
    private int myStopFrame;
    private int myCurrentFrame;
    private boolean myRepeatFlag;
    private List<AnimationTag> myTags;

    private double mySlownessMultiplier;
    private int myFrameCounter;

    /**
     * Initialize the animation and insert a multiplier for slowness
     *
     * @param slownessMultiplier
     *        a multiplier for the speed of the animation. Must be less than
     *        1.
     */
    public AnimationSequence (List<AnimationTag> name,
                              int startFrame,
                              int stopFrame,
                              boolean repeats,
                              double slownessMultiplier) {
        myStartFrame = startFrame;
        myCurrentFrame = startFrame;
        myStopFrame = stopFrame;
        myTags = name;
        myRepeatFlag = repeats;
        if (slownessMultiplier < 1) {
            mySlownessMultiplier = slownessMultiplier;
        }
    }

    public AnimationSequence (List<AnimationTag> name, int startFrame, int stopFrame) {
        myStartFrame = startFrame;
        myCurrentFrame = startFrame;
        myStopFrame = stopFrame;
        myTags = name;
        myRepeatFlag = true;
        mySlownessMultiplier = 1;
    }

    /**
     * Reset the animation to the start frame
     */
    public void reset () {
        myCurrentFrame = myStartFrame;
    }

    /**
     * Increments the animation frame. Return true if the animation is complete.
     */
    @Override
    public boolean update () {
        if (myFrameCounter < 1 / mySlownessMultiplier) {
            myFrameCounter++;
        }
        else {
            myFrameCounter = 0;
            if (myCurrentFrame < myStopFrame) {
                myCurrentFrame++;
                return false;
            }
            else if (myCurrentFrame == myStopFrame && myRepeatFlag) {
                reset();
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

    public List<AnimationTag> getMyName () {
        return myTags;
    }
}