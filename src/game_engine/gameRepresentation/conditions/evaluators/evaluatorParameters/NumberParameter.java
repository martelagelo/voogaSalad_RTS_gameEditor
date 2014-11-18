package game_engine.gameRepresentation.conditions.evaluators.evaluatorParameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.stateManaging.GameElementManager;


/**
 * A basic numeric parameter
 * 
 * @author Zach
 *
 */
public class NumberParameter implements EvaluatorParameter {

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
                            GameElementManager manager,
                            String elementTag,
                            String attributeTag) {
        return Double.toString(myValue);
    }

}
