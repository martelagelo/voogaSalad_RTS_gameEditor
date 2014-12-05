package engine.gameRepresentation.evaluatables.parameters;

import engine.gameRepresentation.evaluatables.ElementPair;

/**
 * A basic string parameter
 *
 * @author Zach
 *
 */
public class StringParameter extends Parameter<String> {

	private String myString;

	/**
	 * Create a string parameter giving the string that the parameter references
	 *
	 * @param value
	 */
	public StringParameter(String id,String string) {
		super(String.class,id);
		myString = string;
	}

	@Override
	public String evaluate(ElementPair elements) {
		return myString;
	}

	@Override
	public boolean setValue(ElementPair elements, String value) {
		myString = value;
		return true;
	}

}
