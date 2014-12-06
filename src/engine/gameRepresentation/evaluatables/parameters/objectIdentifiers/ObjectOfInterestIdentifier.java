package engine.gameRepresentation.evaluatables.parameters.objectIdentifiers;

import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.stateManaging.GameElementManager;
import java.util.List;

/**
 * An interface for a set of classes whose only functionality is to get the
 * objects of interest for creating evaluator parameters. This set of classes
 * was created with the choosing of composition over inheritance for the
 * evaluator parameters. This allows each parameter to act on a wide range of
 * gameElements without requiring a large hierarchy of parameter types.
 *
 * @author Zach
 *
 */
public interface ObjectOfInterestIdentifier {
	/**
	 * Return the element that this object is interested in
	 *
	 * @param elementManager
	 *            the manager that contains all the global objects
	 * @param elementPair
	 *            the object pair that the elements can act on
	 * @param objectTag
	 *            the optional tag of the object type for global references
	 * @return the element of interest
	 */
	public List<GameElement> getElementOfInterest(
			GameElementManager elementManager, ElementPair elementPair,
			String elementTag);

}
