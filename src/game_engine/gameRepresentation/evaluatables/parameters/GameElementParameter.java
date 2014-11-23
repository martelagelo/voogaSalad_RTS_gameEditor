package game_engine.gameRepresentation.evaluatables.parameters;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ObjectOfInterestIdentifier;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.stateManaging.GameElementManager;

/**
 * A parameter that takes in an object of interest identifier and returns the
 * object that is being referenced based on the currently supplied elementPair.
 * 
 * @author Zach
 *
 */
public class GameElementParameter extends Parameter<GameElementState> {

	private GameElementManager myManager;
	private ObjectOfInterestIdentifier myObjectIdentifier;
	private String myElementTag;

	public GameElementParameter(
			ObjectOfInterestIdentifier objectOfInterestIdentifier,
			String elementTag) {
		super(GameElementState.class);
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
		// A parameter does not have the ability to set a referenced element's
		// identifier
		return false;
	}

}
