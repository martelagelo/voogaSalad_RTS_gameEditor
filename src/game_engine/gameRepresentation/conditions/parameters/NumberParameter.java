package game_engine.gameRepresentation.conditions.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;


/**
 * A basic numeric parameter. i.e. a double
 *
 * @author Zach
 *
 */
public class NumberParameter implements Parameter<Number> {

    private Number myValue;

    /**
     * Create a number parameter feeding in a single value
     *
     * @param value
     */
    public NumberParameter (double value) {
        myValue = value;
    }

    @Override
    public Number getValue (ElementPair elements) {
        return myValue;
    }

    @Override
    public boolean setValue (ElementPair elements,
                             Number value) {
        myValue = value;
        return true;
    }

}
