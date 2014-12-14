package engine.visuals.elementVisuals.animations;

/**
 * Enum with Animation Tags allowing for easy reference to specific animation
 * sequences.
 *
 * @author Rahul, Steve
 *
 */
public enum AnimationTag {
    FORWARD(AnimationType.DIRECTION), BACKWARD(AnimationType.DIRECTION), LEFT(
            AnimationType.DIRECTION), RIGHT(AnimationType.DIRECTION), HERE(AnimationType.DIRECTION),

    STAND(AnimationType.ACTION), MOVE(AnimationType.ACTION), ATTACK(AnimationType.ACTION), DIE(
            AnimationType.ACTION), DECAY(AnimationType.ACTION), DO_NOTHING(AnimationType.ACTION);

    private AnimationType myType;

    private AnimationTag (AnimationType type) {
        myType = type;
    }

    public AnimationType getType () {
        return myType;
    }

    public enum AnimationType {
        DIRECTION, ACTION
    }
}
