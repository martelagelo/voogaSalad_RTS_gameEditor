package game_engine.computers.boundsComputers;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.SelectableGameElement;
import java.util.List;

/**
 * Checks for collisions between groups of objects
 *
 * @author Zachary Bears
 *
 */
public class CollisionComputer extends
		Computer<SelectableGameElement, DrawableGameElement> {
	/**
	 * Returns true if there is a collision between the two bounded objects
	 */
	@Override
	protected boolean checkComputingCondition(
			SelectableGameElement primaryObject, DrawableGameElement otherObject) {
		if (primaryObject instanceof Boundable
				&& otherObject instanceof Boundable) {
			Boundable boundableObject = (Boundable) primaryObject;
			Boundable otherBoundableObject = (Boundable) otherObject;
			return boundableObject.getBounds().intersects(
					otherBoundableObject.getBounds());
		} else {
			return false;
		}
	}

	@Override
	protected void addInteractingElementsToObject(
			SelectableGameElement primaryObject,
			List<DrawableGameElement> listToAdd) {
		primaryObject.addCollidingElements(listToAdd);
	}

}
