package engine.gameRepresentation.evaluatables;

/**
 * An object that can evaluate a condition on an element to return a value.
 * Evaluators are generics to allow for the implementation of complex logic with
 * evaluatables. Evaluatables have the option to both get and set values
 * although the value setting is not by default implemented by children of
 * evaluatables. The intent of this interface is to be used in the composite
 * pattern to build large dynamic logic to allow for interaction between game
 * elements and components.
 *
 * @author Zach
 *
 */
public abstract class Evaluatable<T> {
    private Class<T> myType;

    /**
     * Create an evaluatable
     *
     * @param type
     *        the type of the class T of the evaluatable. Used as a
     *        workaround for the poor implementation of generics by Java
     */
    public Evaluatable (Class<T> type) {
        myType = type;
    }

    /**
     * Take in a pair of elements, evaluate a condition on the elements, and
     * return a boolean representing the condition's return value.
     *
     */
    public abstract T evaluate (ElementPair elements);

    /**
     * Sets the evaluatable's value to a given value
     *
     * @param value
     *        the value to set the evaluatable's stored computation value to
     * @return a boolean indicating whether setting was successful
     */
    protected boolean setEvaluatableValue (ElementPair elements, T value) {
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
     * Sets the evaluatable's value to a given value. A generic object must be used
     * due to java generics type erasure. A suppress warnings was added once again due to
     * type erasure. All casting is checked.
     *
     * @param value
     *        the value to set the evaluatable's stored computation value to
     * 
     * @return a boolean indicating whether setting was successful
     */
    @SuppressWarnings("unchecked")
    public boolean setValue (ElementPair myElementPair, Object cast) {
        if (myType.isInstance(cast))
            return setEvaluatableValue(myElementPair, (T) cast);
        return false;

    }
}
