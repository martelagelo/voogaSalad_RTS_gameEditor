package model.state.gameelement;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import model.state.gameelement.traits.Boundable;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.animations.AnimatorState;

/**
 * This GameElement is drawable but not necessarily selectable - examples
 * include terrain. These elements must have a bounding box.
 *
 * @author Steve, Jonathan, Rahul, Zach, Michael D.
 *
 */
public class DrawableGameElementState extends GameElementState implements
		Boundable, Displayable {

	/**
	 * This is a list of wrappers that contain information about widgets that
	 * have to be added to a particular game element. This DisplayerStates will
	 * be generated by the game editor and are utilized by a factory to create
	 * widgets
	 */
	public List<AttributeDisplayerState> AttributeDisplayerStates;

	/**
     * 
     */
	private static final long serialVersionUID = -2449775294910832264L;

	public AnimatorState myAnimatorState;
	private double[] myBounds;

	/**
	 * Create a drawable game element at a given x and y position.
	 *
	 * @param xPosition
	 *            the x position of the element
	 * @param yPosition
	 *            the y position of the element
	 */
	public DrawableGameElementState(Number xPosition, Number yPosition,
			AnimatorState animatorState) {
		super();
		myAnimatorState = animatorState;

		// TODO find better fixes?
		myBounds = new double[8]; // Initialize the bounds to an empty array

		// These positions are stored in a numerical attribute map to allow for
		// easy retrieval of
		// attributes by conditions and actions

		AttributeDisplayerStates = new ArrayList<AttributeDisplayerState>();

		attributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), xPosition);
		attributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), yPosition);

	}

	/**
	 * Adds AttributeDisplayStates to the list of displayerStates
	 * 
	 * @param ADS
	 *            An AttributeDisplayerState
	 */
	public void addAttributeDisplayerState(AttributeDisplayerState ADS) {
		this.AttributeDisplayerStates.add(ADS);
	}

	/**
	 * Removes a particular AttributeDisplayerState from the list of
	 * DisplayerStates
	 * 
	 * @param ADS
	 *            An AttributeDisplayerState
	 */
	public void deleteAttributeDisplayerState(AttributeDisplayerState ADS) {
		for (int i = 0; i < AttributeDisplayerStates.size(); i++) {
			if (AttributeDisplayerStates.get(i) == ADS) {
				AttributeDisplayerStates.remove(i);
				break;
			}
		}
	}

	@Override
	public double[] getBounds() {
		return myBounds;
	}

	/**
	 * Set the bounds
	 *
	 * @param bounds
	 *            the new bounds of the state set in a format that is usable by
	 *            JavaFx's Polygon object
	 */
	public void setBounds(double[] bounds) {
		myBounds = bounds;
	}

	@Override
	public Node getNode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Take an object's bounds and add its x and y position to the bounds to get
	 * the global object bounds
	 */
	public double[] findGlobalBounds() {
		double[] newBounds = myBounds.clone();
		double width = newBounds[2] - newBounds[0];
		double height = newBounds[5] - newBounds[3];
		for (int i = 0; i < newBounds.length; i += 2) {
			newBounds[i] += attributes.getNumericalAttribute(
					StateTags.X_POSITION.getValue()).doubleValue()
					- width / 2;
			newBounds[i + 1] += attributes.getNumericalAttribute(
					StateTags.Y_POSITION.getValue()).doubleValue()
					- height / 2;
		}
		return newBounds;
	}

}
