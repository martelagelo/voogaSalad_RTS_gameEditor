package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.ElementPair;

/**
 * A basic numeric parameter. i.e. a double
 *
 * @author Zach
 *
 */
public class NumberParameter extends Parameter<Number> {

	private Number myValue;

	/**
	 * Create a number parameter feeding in a single value
	 *
	 * @param value
	 */
	public NumberParameter(Number value) {
		super(Number.class);
		myValue = value;
	}

	@Override
	public Number getValue(ElementPair elements) {
		return myValue;
	}

	@Override
	public boolean setValue(ElementPair elements, Number value) {
		myValue = value;
		return true;
	}

}
