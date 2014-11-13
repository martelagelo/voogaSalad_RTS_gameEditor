package vooga.engine.computers.boundsComputers;

import vooga.engine.computers.Computer;


/**
 * Checks for collisions between groups of objects
 *
 * @author Zachary Bears
 *
 */
public class CollisionComputer extends Computer<Bounded, Bounded> {
    /**
     * Returns true if there is a collision between the two bounded objects
     */
    @Override
    protected boolean checkComputingCondition (Bounded primaryObject, Bounded otherObject) {
        return primaryObject.getBounds().equals(otherObject.getBounds());
    }

}
