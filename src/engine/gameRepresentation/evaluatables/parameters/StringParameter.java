package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A basic string parameter that wraps around and returns a string.
 *
 * @author Zach
 *
 */
public class StringParameter extends Parameter<String> {

    private String myString;

    /**
     * Create a string parameter giving the string that the parameter references
     *
     * @param string
     *        the string to wrap
     */
    public StringParameter (String string) {
        super(String.class);
        myString = string;
    }

    @Override
    public String evaluate (ElementPair elements) {
        return myString;
    }

    @Override
    protected boolean setEvaluatableValue (ElementPair elements, String value) {
        myString = value;
        return true;
    }

}
