package engine.visual.animation;

import java.awt.Dimension;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    private Set<AnimationSequence> animationSequenceList;

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
            Set<AnimationSequence> animationSequenceList) {
        this.imageTag = imageTag;
        this.viewportSize = frameDimensions;
        this.numCols = numCols;
        this.animationSequenceList = animationSequenceList;
    }

    public void addAnimationSequence (AnimationSequence seq) {
        animationSequenceList.add(seq);
    }

    public AnimationSequence getAnimationSequence (List<AnimationTag> tags) {
        List<AnimationSequence> animationSequences = animationSequenceList.stream()
                .filter(o -> o.getMyName().equals(tags)).collect(Collectors.toList());
        return (animationSequences.size() != 0) ? animationSequences.get(0) :  new NullAnimationSequence();
    }

    public boolean containsAnimationSequence (AnimationSequence seq) {
        return animationSequenceList.contains(seq);
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
