package engine.computers.boundsComputers;

import java.util.List;
import javafx.scene.shape.Polygon;
import model.state.gameelement.traits.Boundable;
import model.state.gameelement.traits.Sighted;
import engine.computers.Computer;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;


/**
 * A computer that indicates which objects an object with sight can see
 *
 * @author Zachary Bears, Jonathan
 *
 */
public class VisionComputer extends
        Computer<SelectableGameElement, DrawableGameElement> {
    /**
     * Return true if the other object is contained within the primary object's
     * vision bounds
     * 
     * @see Computer#checkComputingCondition
     */
    @Override
    protected boolean checkComputingCondition (
                                               SelectableGameElement primaryObject,
                                               DrawableGameElement otherObject) {
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
                                              SelectableGameElement primaryObject,
                                              List<DrawableGameElement> listToAdd) {
        primaryObject.addInteractingElements("visible", listToAdd);
    }

}
