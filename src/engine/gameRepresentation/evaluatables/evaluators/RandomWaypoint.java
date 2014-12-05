package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that assigns an object's temporary waypoint to a random x and y position near the
 * player
 * 
 * @author Zach
 */
public class RandomWaypoint<A, B> extends Evaluator<A, B, Boolean> {
    // This was not made public as no other classes should depend on any values declared within this
    // evaluator
    private final static double RANDOM_AMOUNT = 100;

    public RandomWaypoint (String id,
                                    Evaluatable<A> parameter1,
                                    Evaluatable<B> parameter2) {
        super(Boolean.class, id, "RandomWaypoint", parameter1, parameter2);
    }

    @Override
    public Boolean evaluate (GameElement element1, GameElement element2) {
        double playerX = element1.getNumericalAttribute(StateTags.X_POSITION).doubleValue();
        double playerY = element1.getNumericalAttribute(StateTags.Y_POSITION).doubleValue();
        // Choose a random new waypoint. The 0.5 was not made into a constant because it is used to
        // simply get a random distribution centered on 0 and is not going to change
        double waypointX = (Math.random() - 0.5) * RANDOM_AMOUNT + playerX;
        double waypointY = (Math.random() - 0.5) * RANDOM_AMOUNT + playerY;

        element1.setNumericalAttribute(StateTags.X_TEMP_HEADING, waypointX);
        element1.setNumericalAttribute(StateTags.Y_TEMP_HEADING, waypointY);

        return true;
    }
}
