package game_engine.gameRepresentation.conditions.evaluators.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;


/**
 * A basic numeric parameter
 *
 * @author Zach
 *
 */
public class NumberParameter implements Parameter {

    private double myValue;

    /**
     * Create a number parameter feeding in a single value
     *
     * @param value
     */
    public NumberParameter (double value) {
        myValue = value;
    }

    @Override
    public String getValue (ElementPair elements,
                            String elementTag) {
        return Double.toString(myValue);
    }

    @Override
    public boolean setValue (ElementPair elements,
                             String elementTag,
                             String value) {
        myValue = Double.valueOf(value);
        return true;
    }

}
