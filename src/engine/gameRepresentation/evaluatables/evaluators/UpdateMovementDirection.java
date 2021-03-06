package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;

/**
 * An evaluator that updates a game element's movement direction based on the
 * heading position the element
 *
 * @author Zach
 */
public class UpdateMovementDirection<A, B> extends Evaluator<A, B, Boolean> {

    public UpdateMovementDirection (Evaluatable<A> parameter1, Evaluatable<B> parameter2) {
        super(Boolean.class, "updateMovementDirection", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        double speed = element1.getNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue())
                .doubleValue();
        double deltaX = element1.getNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION.getValue())
                .doubleValue()
                - element1.getNumericalAttribute(StateTags.X_POSITION.getValue()).doubleValue();
        double deltaY = element1.getNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION.getValue())
                .doubleValue()
                - element1.getNumericalAttribute(StateTags.Y_POSITION.getValue()).doubleValue();
        // Truncate the x and y differences if they're less than a fraction of
        // the element's speed
        deltaX = deltaX > speed || deltaX < -speed ? deltaX : 0;
        deltaY = deltaY > speed || deltaY < -speed ? deltaY : 0;
        double direction = Math.atan2(deltaY, deltaX);
        if (deltaX != 0 || deltaY != 0) {
            element1.setNumericalAttribute(StateTags.X_VELOCITY.getValue(),
                    Math.round(speed * Math.cos(direction)));
            element1.setNumericalAttribute(StateTags.Y_VELOCITY.getValue(),
                    Math.round(speed * Math.sin(direction)));
        } else {
            element1.setNumericalAttribute(StateTags.X_VELOCITY.getValue(), 0);
            element1.setNumericalAttribute(StateTags.Y_VELOCITY.getValue(), 0);
        }
        return true;
    }

}
