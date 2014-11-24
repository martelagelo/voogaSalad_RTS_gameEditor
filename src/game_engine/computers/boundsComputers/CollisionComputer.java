package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.Boundable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import javafx.scene.shape.Polygon;


/**
 * Checks for collisions between groups of objects and give the primary object references
 *
 * @author Zach, Jonathan, Rahul, Nishad
 *
 */
public class CollisionComputer extends
        Computer<SelectableGameElementState, DrawableGameElementState> {
    /**
     * Returns true if there is a collision between the two bounded objects
     *
     * @see Computer#checkComputingCondition
     */
    @Override
    protected boolean checkComputingCondition (
                                               SelectableGameElementState primaryObject,
                                               DrawableGameElementState otherObject) {
        if (primaryObject instanceof Boundable
            && otherObject instanceof Boundable) {
            Boundable boundableObject = primaryObject;
            Boundable otherBoundableObject = otherObject;
            return new Polygon(boundableObject.getBounds())
                    .intersects(new Polygon(otherBoundableObject.getBounds()).getBoundsInLocal());
        }
        else return false;
    }

    /**
     * Give the primary object the objects to add in its colliding elements collection
     *
     * @see Computer#givePrimaryObjectElements
     */
    @Override
    protected void givePrimaryObjectElements (
                                              SelectableGameElementState primaryObject,
                                              List<DrawableGameElementState> listToAdd) {
        primaryObject.addCollidingElements(listToAdd);
    }

}
