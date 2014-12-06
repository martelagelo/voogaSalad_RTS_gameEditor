package engine.gameRepresentation.evaluatables;

/**
 * An object that can evaluate a condition on an element to return a value.
 * Evaluators are generics to allow for the implementation of complex logic with
 * evaluatables. Evaluatables have the option to both get and set values
 * although the value setting is not by default implemented by children of
 * evaluatable.
 *
 * @author Zach
 *
 */
public abstract class Evaluatable<T> {
    private Class<T> myType;
    private String myID;

    /**
     * Create an evaluatable
     * 
     * @param type
     *        the type of the class T of the evaluatable. Used as a
     *        workaround for the poor implementation of generics by Java
     * @param id an id that will be shared amongst all elements of an evaluatable tree. it allows
     *        leaves of the tree to reference higher up nodes.
     */
    public Evaluatable (Class<T> type, String id) {
        myType = type;
        myID = id;
    }

    /**
     * Take in a pair of elements, evaluate a condition on the elements, and
     * return a boolean representing the condition's return value.
     *
     */
    public abstract T evaluate (ElementPair elements);

    /**
     * Use the null pattern to evaluate a condition if no elements are given
     */
    public T evaluate () {
        return evaluate(new NullElementPair());
    }

    /**
     * Sets the evaluatable's value to a given value
     * 
     * @param value
     *        the value to set the evaluatable's stored computation value to
     * @return a boolean indicating whether setting was successful
     */
    public boolean setValue (ElementPair elements, T value) {
        return false;
    }

    /**
     * Use the null pattern to set a value if no element pair is applicable
     */
    public boolean setValue (T value) {
        return setValue(new NullElementPair(), value);
    }

    /**
     * Get the type of the object as a way of bypassing Java type erasure
     * 
     * @return the generic's type
     */
    public Class<T> getType () {
        return myType;
    }

    /**
     * Get the current ID of the evlauatable
     * 
     * @retrun the evaluatable's ID string
     */
    public String getID () {
        return myID;
    }
}