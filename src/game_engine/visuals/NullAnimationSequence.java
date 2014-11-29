package game_engine.visuals;

/**
 * An empty animation following the Null pattern
 *
 * @author Zach, Rahul
 *
 */
public class NullAnimationSequence extends AnimationSequence {

    public NullAnimationSequence () {
        // Call super constructor setting next animation sequence to be null
        super("", 0, 0, null);

    }

}
