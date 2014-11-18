package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import javafx.scene.shape.Polygon;


/**
 * Checks for collisions between groups of objects
 *
 * @author Zachary Bears, Jonathan, Rahul, Nishad
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
            return new Polygon(boundableObject.getBounds())
                    .intersects(new Polygon(otherBoundableObject.getBounds()).getBoundsInLocal());
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
