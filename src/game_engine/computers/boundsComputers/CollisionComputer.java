package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;


/**
 * Checks for collisions between groups of objects
 *
 * @author Zachary Bears
 *
 */
public class CollisionComputer extends
        Computer<SelectableGameElementState, DrawableGameElementState> {
    /**
     * Returns true if there is a collision between the two bounded objects
     */
    @Override
    protected boolean checkComputingCondition (
                                               SelectableGameElementState primaryObject,
                                               DrawableGameElementState otherObject) {
        if (primaryObject instanceof Boundable
            && otherObject instanceof Boundable) {
            Boundable boundableObject = (Boundable) primaryObject;
            Boundable otherBoundableObject = (Boundable) otherObject;
            return boundableObject.getBounds().intersects(
                                                          otherBoundableObject.getBounds());
        }
        else {
            return false;
        }
    }

    @Override
    protected void addInteractingElementsToObject (
                                                   SelectableGameElementState primaryObject,
                                                   List<DrawableGameElementState> listToAdd) {
        primaryObject.addCollidingElements(listToAdd);
    }

}
