package game_engine.gameRepresentation.conditions.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;

/**
 * A basic numeric parameter. i.e. a double
 *
 * @author Zach
 *
 */
public class NumberParameter extends Parameter<Double> {

	private Double myValue;

	/**
	 * Create a number parameter feeding in a single value
	 *
	 * @param value
	 */
	public NumberParameter(Double value) {
		super(null);
		myValue = value;
	}

	@Override
	public Double evaluate(ElementPair elements) {
		return myValue;
	}

	@Override
	public boolean setValue(ElementPair elements, Double value) {
		myValue = value;
		return true;
	}

}
