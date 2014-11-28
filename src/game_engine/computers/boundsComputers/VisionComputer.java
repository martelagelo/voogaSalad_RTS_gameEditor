package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Boundable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Sighted;
import java.util.List;
import javafx.scene.shape.Polygon;


/**
 * A computer that indicates which objects an object with sight can see
 *
 * @author Zachary Bears, Jonathan
 *
 */
public class VisionComputer extends
        Computer<SelectableGameElementState, DrawableGameElementState> {
    /**
     * Return true if the other object is contained within the primary object's
     * vision bounds
     * 
     * @see Computer#checkComputingCondition
     */
    @Override
    protected boolean checkComputingCondition (
                                               SelectableGameElementState primaryObject,
                                               DrawableGameElementState otherObject) {
        if (primaryObject instanceof Sighted
            && otherObject instanceof Boundable) {
            Sighted sightedObject = (Sighted) primaryObject;
            Boundable boundableObject = (Boundable) otherObject;
            return new Polygon(sightedObject.getVisionBounds())
                    .intersects(new Polygon(boundableObject.getBounds()).getBoundsInLocal());
        }
        else {
            return false;
        }
    }

    /**
     * Add the elements of interest to the primary object's visible elements list
     * 
     * @see Computer#givePrimaryObjectElements
     */
    @Override
    protected void givePrimaryObjectElements (
                                              SelectableGameElementState primaryObject,
                                              List<DrawableGameElementState> listToAdd) {
        primaryObject.addVisibleElements(listToAdd);
    }

}
