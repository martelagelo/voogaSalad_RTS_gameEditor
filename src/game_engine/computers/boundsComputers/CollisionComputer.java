package game_engine.computers.boundsComputers;

import game_engine.computers.boundsComputer.Boundable;
import game_engine.computers.Computer;


/**
 * Checks for collisions between groups of objects
 *
 * @author Zachary Bears
 *
 */
public class CollisionComputer extends Computer<Boundable, Boundable> {
    /**
     * Returns true if there is a collision between the two bounded objects
     */
    @Override
    protected boolean checkComputingCondition (Boundable primaryObject, Boundable otherObject) {
        return primaryObject.getBounds().equals(otherObject.getBounds());
    }

}
