package game_engine.gameRepresentation.conditions.parameters;

import game_engine.gameRepresentation.conditions.ElementPair;
import game_engine.gameRepresentation.conditions.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;

public class GameElementParameter implements Parameter<GameElementState> {

	private GameElementManager myManager;
	private ObjectOfInterestIdentifier myObjectIdentifier;
	private String myElementTag;

	public GameElementParameter(
			ObjectOfInterestIdentifier objectOfInterestIdentifier,
			String elementTag) {
		myObjectIdentifier = objectOfInterestIdentifier;
		myElementTag = elementTag;

	}

	@Override
	public GameElementState getValue(ElementPair elements) {
		return myObjectIdentifier.getElementOfInterest(myManager, elements,
				myElementTag).get(0);
	}

	@Override
	public boolean setValue(ElementPair elements, GameElementState value) {
		//A parameter does not have the ability to set a referenced element's identifier
		return false;
	}

}
