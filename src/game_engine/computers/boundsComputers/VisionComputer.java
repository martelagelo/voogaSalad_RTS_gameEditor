package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.DrawableGameElement;
import java.util.List;


/**
 * A computer that indicates which objects an object with sight can see
 *
 * @author Zachary Bears
 *
 */
public class VisionComputer extends Computer<DrawableGameElement> {
    /**
     * Return true if the other object is contained within the primary object's vision bounds
     */
    @Override
    protected boolean checkComputingCondition (DrawableGameElement primaryObject,
                                               DrawableGameElement otherObject) {
        if (primaryObject instanceof Sighted && otherObject instanceof Boundable) {
            Sighted sightedObject = (Sighted) primaryObject;
            Boundable boundableObject = (Boundable) otherObject;
            return sightedObject.getVisionPolygon().intersects(boundableObject.getBounds());
        }
        else {
            return false;
        }
    }

    @Override
    protected void addInteractingElementsToObject (DrawableGameElement primaryObject,
                                                   List<DrawableGameElement> listToAdd) {
        primaryObject.addVisibleElements(listToAdd);
    }

}
