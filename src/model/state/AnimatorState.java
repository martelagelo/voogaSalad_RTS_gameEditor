package model.state;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import util.JSONable;
import engine.visual.animation.AnimationSequence;
import engine.visual.animation.NullAnimationSequence;


/**
 * A data wrapper object used to group the pertinent information for a
 * spritesheet. Internal data is intentionally made public as this is just a
 * data wrapper to simplify the passing of data
 * 
 * @author Rahul, Zach, Steve
 *
 */

public class AnimatorState implements JSONable {

    private String imageTag;
    private Dimension viewportSize;
    private int numCols;
    private Map<List<AnimationTag>, AnimationSequence> animationMap;

    /**
     * Create the Spritesheet
     * 
     * @param imageTag
     *        the tag for the image that will be sent to the save/load
     *        utility to get the image
     * @param frameDimensions
     *        the dimensions of a frame of the spritesheet
     * @param numCols
     *        the number of columns across in the spritesheet
     * @param animationMap
     * 		  map of Animation tags to Animation sequences
     */
    public AnimatorState (String imageTag,
                          Dimension frameDimensions,
                          int numCols,
                          Map<List<AnimationTag>, AnimationSequence> animationMap) {
        this.imageTag = imageTag;
        this.viewportSize = frameDimensions;
        this.numCols = numCols;
        this.animationMap = animationMap;
    }

    /**
     * Adds an animation to the AnimatorState's animationMap
     * @param tag
     * 		  a list of animation tags which serves as a key in
     *        animationMap
     * @param seq
     *        an animation sequence to which the tag is mapped
     */
    public void addAnimationSequence (List<AnimationTag> tag, AnimationSequence seq) {
        animationMap.put(tag, seq);
    }

    /**
     * Returns the associated animation sequence given an animation
     * tag list as a key. If the animation sequence is not in the map,
     * a null animation sequence is returned
     * 
     * @param tag
     *        a list of animation tags which serves as a key in 
     *        animationMap
     * @return AnimationSequence
     * 		   the animation sequence to which the tag is mapped
     *         if there is no mapping to the tag, a NullAnimationSequence
     *         is returned.
     */
    public AnimationSequence getAnimationSequence (List<AnimationTag> tag) {
        return (animationMap.containsKey(tag)) ? animationMap.get(tag)
                                              : new NullAnimationSequence();
    }

    /**
     * Checks if the given list of animation tags is in the keyset of
     * the animationMap
     * 
     * @param tag
     *        a list of animation tags which serves as a key in 
     *        animationMap
     * @return boolean
     *         true if the tag is in the keyset of the animationMap
     *         false if the tag is not in the keyset of the animationMap
     */
    public boolean containsAnimationSequence (List<AnimationTag> tag) {
        return animationMap.containsKey(tag);
    }

    /**
     * Returns the imageTag of this instance of AnimatorState
     * 
     * @return String
     *         the imageTag of the AnimatorState
     */
    public String getImageTag () {
        return imageTag;
    }

    /**
     * Returns the size of the viewport
     * 
     * @return Dimension
     *         a dimensions object which contains
     *         the size of the user's screen
     */
    public Dimension getViewportSize () {
        return viewportSize;
    }

    /**
     * Returns the number of columns in the spritesheet
     * 
     * @return int
     *         the number of columns in the spritesheet
     */
    public int getNumCols () {
        return numCols;
    }
}
