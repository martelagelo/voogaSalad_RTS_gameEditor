package engine.visual.animation;

import java.util.ArrayList;
import java.util.Arrays;
import model.state.AnimationTag;


/**
 * An empty animation following the Null pattern
 *
 * @author Zach, Rahul, Steve
 *
 */
public class NullAnimationSequence extends AnimationSequence {

    public NullAnimationSequence () {
        super(
              new ArrayList<AnimationTag>(
                                          Arrays.asList(new AnimationTag[] { AnimationTag.DO_NOTHING })),
              0, 0, true, 1);
        // Call super constructor setting next animation sequence to be null
        // TODO fix animation tag passed in
    }

}