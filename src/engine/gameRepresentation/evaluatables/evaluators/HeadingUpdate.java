package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that updates the temp heading to the heading if the temp heading was reached
 * 
 * @author Zach
 *
 */
public class HeadingUpdate<A, B> extends Evaluator<A, B, Boolean> {

    public HeadingUpdate (String id,
                                   Evaluatable<A> parameter1,
                                   Evaluatable<B> parameter2) {
        super(Boolean.class, id, "Heading update", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        double xPos = element1.getNumericalAttribute(StateTags.X_POSITION).doubleValue();
        double yPos = element1.getNumericalAttribute(StateTags.Y_POSITION).doubleValue();
        double tempXGoal = element1.getNumericalAttribute(StateTags.X_TEMP_HEADING).doubleValue();
        double tempYGoal = element1.getNumericalAttribute(StateTags.Y_TEMP_HEADING).doubleValue();
        double speed = element1.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue();
        // If we're at our temp goal, set our goal to our main goal
        if (Math.abs(xPos - tempXGoal) < speed && Math.abs(yPos - tempYGoal) < speed) {
            element1.setNumericalAttribute(StateTags.X_TEMP_HEADING,
                                           element1.getNumericalAttribute(StateTags.X_HEADING));
            element1.setNumericalAttribute(StateTags.Y_TEMP_HEADING,
                                           element1.getNumericalAttribute(StateTags.Y_HEADING));
        }
        return true;
    }

}
