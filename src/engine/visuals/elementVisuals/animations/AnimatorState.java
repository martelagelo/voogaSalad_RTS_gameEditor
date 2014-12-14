package engine.visuals.elementVisuals.animations;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
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
    private String myImageTag;
    private String myColorMaskTag;
    private Dimension myViewportSize;
    private int myNumRows;
    private Set<AnimationSequence> myAnimationSquences;

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
    public AnimatorState (String imageTag, String colorMaskTag, Dimension frameDimensions,
            int numRows, Set<AnimationSequence> animationSequences) {
        myColorMaskTag = colorMaskTag;
        myImageTag = imageTag;
        myViewportSize = frameDimensions;
        myNumRows = numRows;
        myAnimationSquences = animationSequences;
        if (myAnimationSquences == null) {
            myAnimationSquences = new HashSet<>();
            myAnimationSquences.add(new NullAnimationSequence());
        }
    }

    public void addAnimationSequence (AnimationSequence seq) {
        myAnimationSquences.add(seq);
    }

    public AnimationSequence getAnimationSequence (List<AnimationTag> tags) {
        List<AnimationSequence> matchingAnimationSequences = myAnimationSquences.stream()
                .filter(o -> o.getTags().equals(tags)).collect(Collectors.toList());
        return (matchingAnimationSequences.size() != 0) ? matchingAnimationSequences.get(0)
                : new NullAnimationSequence();
    }

    public Set<AnimationSequence> getAllAnimationSequences () {
        return Collections.unmodifiableSet(myAnimationSquences);
    }

    public boolean containsAnimationSequence (AnimationSequence seq) {
        return myAnimationSquences.contains(seq);
    }

    public String getImageTag () {
        return myImageTag;
    }

    public String getColorMaskTag () {
        return myColorMaskTag;
    }

    public Dimension getViewportSize () {
        return myViewportSize;
    }

    public int getNumRows () {
        return myNumRows;
    }
}
