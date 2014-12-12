package engine.gameRepresentation.evaluatables.evaluators;

import model.state.gameelement.StateTags;
import com.sun.javafx.geom.Point2D;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.renderedRepresentation.GameElement;


/**
 * An evaluator that implements classical game bouncing. element 1 bounces off of element 2
 * 
 * @author Zach
 *
 */
public class Bounce<A, B> extends Evaluator<A, B, Boolean> {

    public Bounce (String id,
                   Evaluatable<A> parameter1,
                   Evaluatable<B> parameter2) {
        super(Boolean.class, id, "bounce", parameter1, parameter2);
    }

    @Override
    protected Boolean evaluate (GameElement element1, GameElement element2) {
        Point2D element1Center = getCenter(element1);
        Point2D element2Center = getCenter(element2);
        double speed = element1.getNumericalAttribute(StateTags.MOVEMENT_SPEED.getValue()).doubleValue();
        double deltaY = element1Center.y - element2Center.y;
        double deltaX = element1Center.x - element2Center.x;
        double angle = Math.atan2(deltaY, deltaX);
        element1.setNumericalAttribute(StateTags.X_VELOCITY.getValue(), Math.round(speed * Math.cos(angle)));
        element1.setNumericalAttribute(StateTags.Y_VELOCITY.getValue(), Math.round(speed * Math.sin(angle)));
        return true;

    }

    private Point2D getCenter (GameElement element) {
        Point2D center = new Point2D();
        center.setLocation(element.getNumericalAttribute(StateTags.X_POSITION.getValue()).floatValue(),
                           element.getNumericalAttribute(StateTags.Y_POSITION.getValue()).floatValue());
        return center;
    }

}
