package engine.visuals.elementVisuals.animations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import util.JSONable;
import engine.visuals.Dimension;

/**
 * A data wrapper object used to group the pertinent information for a
 * spritesheet. Internal data is intentionally made public as this is just a
 * data wrapper to simplify the passing of data
 * 
 * @author Rahul, Zach, Steve
 *
 */

public class AnimatorState implements JSONable, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1609072629703433749L;
    private String imageTag;
    private String colorMaskTag;
    private Dimension viewportSize;
    private int numRows;
    private Set<AnimationSequence> animationSequences;

    /**
     * Create the Spritesheet
     * 
     * @param imageTag
     *            the tag for the image that will be sent to the save/load
     *            utility to get the image
     * @param frameDimensions
     *            the dimensions of a frame of the spritesheet
     * @param numRows
     *            the number of columns across in the spritesheet
     */
    public AnimatorState (String imageTag, String colorMaskTag, Dimension frameDimensions, int numRows,
            Set<AnimationSequence> animationSequenceList) {
        this.colorMaskTag = colorMaskTag;
        this.imageTag = imageTag;
        this.viewportSize = frameDimensions;
        this.numRows = numRows;
        this.animationSequences = animationSequenceList;
    }

    public void addAnimationSequence (AnimationSequence seq) {
        animationSequences.add(seq);
    }

    public AnimationSequence getAnimationSequence (List<AnimationTag> tags) {
        List<AnimationSequence> matchingAnimationSequences = animationSequences.stream()
                .filter(o -> o.getTags().equals(tags)).collect(Collectors.toList());
        return (matchingAnimationSequences.size() != 0) ? matchingAnimationSequences.get(0) :  new NullAnimationSequence();
    }

    public boolean containsAnimationSequence (AnimationSequence seq) {
        return animationSequences.contains(seq);
    }

    public String getImageTag () {
        return imageTag;
    }

    public Dimension getViewportSize () {
        return viewportSize;
    }

    public int getNumRows () {
        return numRows;
    }
}
