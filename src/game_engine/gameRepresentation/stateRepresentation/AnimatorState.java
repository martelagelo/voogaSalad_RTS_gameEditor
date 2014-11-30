package game_engine.gameRepresentation.stateRepresentation;

import game_engine.visuals.Dimension;
import game_engine.visuals.elementVisuals.animations.AnimationSequence;
import game_engine.visuals.elementVisuals.animations.NullAnimationSequence;
import java.util.List;
import java.util.Map;
import util.JSONable;


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

    public void addAnimationSequence (List<AnimationTag> tag, AnimationSequence seq) {
        animationMap.put(tag, seq);
    }

    public AnimationSequence getAnimationSequence (List<AnimationTag> tag) {
        return (animationMap.containsKey(tag)) ? animationMap.get(tag) : new NullAnimationSequence();
    }

    public boolean containsAnimationSequence (List<AnimationTag> tag) {
        return animationMap.containsKey(tag);
    }
    
    public String getImageTag () {
        return imageTag;
    }

    public Dimension getViewportSize () {
        return viewportSize;
    }

    public int getNumCols () {
        return numCols;
    }
}
