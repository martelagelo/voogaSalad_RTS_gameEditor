package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;


/**
 * A basic string parameter
 *
 * @author Zach
 *
 */
public class StringParameter implements Parameter {

    private String myString;

    /**
     * Create a number parameter feeding in a single value
     *
     * @param value
     */
    public StringParameter (String string) {
        myString = string;
    }

    @Override
    public String getValue (ElementPair elements) {
        return myString;
    }

    @Override
    public boolean setValue (ElementPair elements, String value) {
        myString = value;
        return true;
    }

}
