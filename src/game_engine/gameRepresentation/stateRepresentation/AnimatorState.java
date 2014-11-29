package game_engine.gameRepresentation.stateRepresentation;

import game_engine.visuals.AnimationSequence;
import game_engine.visuals.Dimension;

import java.util.Map;

import util.JSONable;

/**
 * A data wrapper object used to group the pertinent information for a
 * spritesheet. Internal data is intentionally made public as this is just a
 * data wrapper to simplify the passing of data
 * 
 * @author Rahul, Zach
 *
 */

public class AnimatorState implements JSONable {

    public String imageTag;
    public Dimension frameDimensions;
    public int numCols;
    public Map<AnimationTags, AnimationSequence> animationMap;

    /**
     * Create the Spritesheet
     * 
     * @param imageTag
     *            the tag for the image that will be sent to the save/load
     *            utility to get the image
     * @param frameDimensions
     *            the dimensions of a frame of the spritesheet
     * @param numCols
     *            the number of columns across in the spritesheet
     */
    public AnimatorState (String imageTag, Dimension frameDimensions, int numCols,
            Map<AnimationTags, AnimationSequence> animationMap) {
        this.imageTag = imageTag;
        this.frameDimensions = frameDimensions;
        this.numCols = numCols;
        this.animationMap = animationMap;
    }

    public void addAnimationSequence (AnimationTags tag, AnimationSequence seq) {
        if (animationMap != null)
            animationMap.put(tag, seq);
    }

    public AnimationSequence getAnimationSequence (AnimationTags tag) {
        return (animationMap != null) ? animationMap.get(tag) : null;
    }

}
