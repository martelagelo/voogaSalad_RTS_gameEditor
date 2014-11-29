package game_engine.visuals;

import game_engine.gameRepresentation.stateRepresentation.AnimationTags;

/**
 * An empty animation following the Null pattern
 *
 * @author Zach, Rahul
 *
 */
public class NullAnimationSequence extends AnimationSequence {

    public NullAnimationSequence () {
        // Call super constructor setting next animation sequence to be null
        // TODO fix animation tag passed in
        super(AnimationTags.DO_NOTHING, 0, 0, null);

    }

}
