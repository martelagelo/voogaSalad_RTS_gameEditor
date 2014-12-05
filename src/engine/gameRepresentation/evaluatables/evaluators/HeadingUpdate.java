package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.computers.pathingComputers.Location;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
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
        DrawableGameElement element = (DrawableGameElement) element1; // only drawableElements can
                                                                      // move
        double xPos = element.getNumericalAttribute(StateTags.X_POSITION).doubleValue();
        double yPos = element.getNumericalAttribute(StateTags.Y_POSITION).doubleValue();
        double tempXGoal =
                element.getNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION).doubleValue();
        double tempYGoal =
                element.getNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION).doubleValue();
        double xGoal = element.getNumericalAttribute(StateTags.X_GOAL_POSITION).doubleValue();
        double yGoal = element.getNumericalAttribute(StateTags.Y_GOAL_POSITION).doubleValue();
        double speed = element.getNumericalAttribute(StateTags.MOVEMENT_SPEED).doubleValue();
        // If we're at our goal, set our goal to our next goal
        if (Math.abs(xPos - xGoal) <= speed && Math.abs(yPos - yGoal) <= speed) {
            Location waypoint = element.getNextWaypoint();
            element.setNumericalAttribute(StateTags.X_GOAL_POSITION, waypoint.myX);
            element.setNumericalAttribute(StateTags.Y_GOAL_POSITION, waypoint.myY);
        }
        // If we're at our temp goal, set our temp goal to our goal
        if (Math.abs(xPos - tempXGoal) <= speed && Math.abs(yPos - tempYGoal) <= speed) {
            element.setNumericalAttribute(StateTags.X_TEMP_GOAL_POSITION,
                                          element.getNumericalAttribute(StateTags.X_GOAL_POSITION));
            element.setNumericalAttribute(StateTags.Y_TEMP_GOAL_POSITION,
                                          element.getNumericalAttribute(StateTags.Y_GOAL_POSITION));
        }
        return true;
    }

}
