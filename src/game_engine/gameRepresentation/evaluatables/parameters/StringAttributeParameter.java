package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.stateManaging.GameElementManager;

import java.util.List;

/**
 * An attribute parameter that acts on a string from the given object
 *
 * @author Zach
 *
 */
public class StringAttributeParameter extends AttributeParameter<String> {
	/**
	 * @see AttributeParameter
	 */
	public StringAttributeParameter(String attributeTag,
			GameElementManager manager,
			ObjectOfInterestIdentifier objectOfInterestIdentifier) {
		super(String.class, attributeTag, manager, objectOfInterestIdentifier);
	}

	/**
	 * Return the first string with the tag from the list of elements of
	 * interest
	 * 
	 * @see AttributeParameter#getValue
	 */
	@Override
	public String getValue(List<GameElement> elements, String attributeTag) {
		return (elements.size() > 0) ? elements.get(0).getGameElementState()
				.getTextualAttribute(attributeTag) : "";
	}

	@Override
	public boolean setValue(List<GameElement> elements, String attributeTag,
			String value) {
		elements.forEach(element -> element.getGameElementState()
				.setTextualAttribute(attributeTag, value));
		return true;
	}

}