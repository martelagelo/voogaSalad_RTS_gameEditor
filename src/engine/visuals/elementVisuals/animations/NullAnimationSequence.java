package engine.visuals.elementVisuals.animations;

import java.util.ArrayList;


/**
 * An empty animation following the Null pattern
 *
 * @author Zach, Rahul, Steve
 *
 */
public class NullAnimationSequence extends AnimationSequence {

    /**
     * 
     */
    private static final long serialVersionUID = 2526081900070531108L;
    public NullAnimationSequence () {
        super(new ArrayList<AnimationTag>() {
            {
                add(AnimationTag.DO_NOTHING);
            }
        }, 0, 1, true, 1);
        // Call super constructor setting next animation sequence to be null
        // TODO fix animation tag passed in
    }

}
