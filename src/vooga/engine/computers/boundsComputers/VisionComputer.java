package vooga.engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.Bounded;


/**
 * A computer that indicates which objects an object with sight can see
 *
 * @author Zachary Bears
 *
 */
public class VisionComputer extends Computer<Sighted, Bounded> {
    /**
     * Return true if the other object is contained within the primary object's vision bounds
     */
    @Override
    protected boolean checkComputingCondition (Sighted primaryObject, Bounded otherObject) {
        return primaryObject.getVisionPolygon().intersects(otherObject.getBounds());
    }

}
