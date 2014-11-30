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
        super(new ArrayList<AnimationTag>() {
            {
                add(AnimationTag.DO_NOTHING);
            }
        }, 0, 0, true, 1);
        // Call super constructor setting next animation sequence to be null
        // TODO fix animation tag passed in
    }

}
