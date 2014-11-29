package game_engine.computers;

import java.util.List;
import java.util.stream.Collectors;


/**
 * An class following the strategy pattern to allow for a large variety of
 * computing classes that take in an object of interest and a list of objects to
 * compute against. A computer goes through all the objects in the list of objects to compute
 * against, checks if they meet the criteria, and, if they do, adds them to the object that requires
 * the objects.
 *
 * @author Zach
 *
 * @param <T>
 *        The type of object the computer will be interacting with
 */
public abstract class Computer<T, E> {
    /**
     * Take in the object of interest and and a list of objects to check. For each object in the
     * list of objects to check, check if it meets the criteria defined by the computer. If the
     * object does meet the criteria defined by the computer, add it to a list of objects to pass to
     * the primary object. Then, add the objects to the primary object using the
     * givePrimeryObjectElements method.
     *
     * @param primaryObject the primary object to check objects against
     * @param objectsToCheck a list of all the objects to check against the primary object
     *
     */
    public void compute (T primaryObject, List<E> objectsToCheck) {
        List<E> listToAdd = objectsToCheck.stream()
                .filter(o -> !primaryObject.equals(o)&&checkComputingCondition(primaryObject, o))
                .collect(Collectors.toList());
        givePrimaryObjectElements(primaryObject, listToAdd);
    }

    /**
     * Given a list of the objects of interest and a primary object, give the primary object the
     * objects of interest.
     *
     * @param primaryObject the primary object
     * @param objectsOfInterest the objects of interest to add to the primary object
     */
    protected abstract void givePrimaryObjectElements (T primaryObject,
                                                       List<E> objectsOfInterest);

    /**
     * Returns true if a condition between the two objects is satisfied
     *
     * @param primaryObject the object initiating the check
     * @param otherObject the object to check against
     */
    protected abstract boolean checkComputingCondition (T primaryObject,
                                                        E otherObject);

}
