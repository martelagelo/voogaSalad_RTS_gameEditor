package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.ElementPair;


/**
 * A random parameter that takes in two values and returns a random number between them
 * 
 * @author Zach
 *
 */
public class RandomParameter extends Parameter<Number> {

    private double myLowerBound;
    private double myUpperBound;

    /**
     * Create a random parameter that, when evaluated, returns a random number evenly distributed
     * between the lowerBound and upperBound
     * 
     * @param lowerBound the lower number for the randomly generated number
     * @param upperBound the upper number for the randomly generated number
     */
    public RandomParameter (double lowerBound, double upperBound) {
        super(Number.class);
        myLowerBound = lowerBound;
        myUpperBound = upperBound;
    }

    @Override
    public Number getValue (ElementPair elements) {
        return Math.random() * Math.abs(myUpperBound - myLowerBound) + myLowerBound;
    }

    @Override
    public boolean setValue (ElementPair elements, Number value) {
        return false;
    }

}
