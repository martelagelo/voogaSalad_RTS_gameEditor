package vooga.engine.computers;

import java.util.List;
import java.util.stream.Collectors;


/**
 * An interface following the strategy pattern to allow for a large variety of
 * computing classes that take in an object of interest and a list of objects to compute against and
 * returns the objects in the list that meet a criteria with the given object of interest
 * 
 * @author Zachary Bears
 *
 * @param <T> The type of object the computer will be interacting with
 */
public abstract class Computer<T,E> {
    /**
     * Take in the object of interest and return a list of the objects that match the computer's
     * criteria relative to the primary object. Return these objects in a list for use by other
     * modules.
     * 
     */
    public List<E> compute (T primaryObject, List<E> objectsToCheck){
        return objectsToCheck.stream()
                .filter(o -> checkComputingCondition(primaryObject,o))
                .collect(Collectors.toList());
    }
    /**
     * Returns true if a condition between the two objects is satisfied
     * @param primaryObject
     * @param otherObject
     * @return
     */
    protected abstract boolean checkComputingCondition(T primaryObject, E otherObject);

}
