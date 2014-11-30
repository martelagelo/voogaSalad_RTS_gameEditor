package game_engine.visuals.elementVisuals.animations;

import game_engine.gameRepresentation.stateRepresentation.AnimationTag;
import java.util.ArrayList;

/**
 * An empty animation following the Null pattern
 *
 * @author Zach, Rahul, Steve
 *
 */
public class NullAnimationSequence extends AnimationSequence {

    public NullAnimationSequence () {
        super(new ArrayList<AnimationTag>(){{
            final long serialVersionUID = 1L;
            add(AnimationTag.DO_NOTHING);}}, 0, 0, null);
        // Call super constructor setting next animation sequence to be null
        // TODO fix animation tag passed in
    }

}
