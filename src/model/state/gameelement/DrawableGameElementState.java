package model.state.gameelement;

import javafx.scene.Node;
import model.state.gameelement.traits.Boundable;
import engine.visuals.Displayable;
import engine.visuals.elementVisuals.animations.AnimatorState;


/**
 * This GameElement is drawable but not necessarily selectable - examples
 * include terrain. These elements must have a bounding box.
 *
 * @author Steve, Jonathan, Rahul, Zach
 *
 */

public class DrawableGameElementState extends GameElementState implements Boundable, Displayable {

    /**
     * 
     */
    private static final long serialVersionUID = -2449775294910832264L;
    
    public AnimatorState myAnimatorState;
    private double[] myBounds;

    /**
     * Create a drawable game element at a given x and y position.
     *
     * @param xPosition the x position of the element
     * @param yPosition the y position of the element
     */
    public DrawableGameElementState (Number xPosition, Number yPosition, AnimatorState animatorState) {
        super();
        myAnimatorState = animatorState;

        // TODO find better fixes?
        myBounds = new double[8]; // Initialize the bounds to an empty array

        // These positions are stored in a numerical attribute map to allow for
        // easy retrieval of
        // attributes by conditions and actions

        attributes.setNumericalAttribute(StateTags.X_POSITION, xPosition);
        attributes.setNumericalAttribute(StateTags.Y_POSITION, yPosition);
    }

    @Override
    public double[] getBounds () {
        return myBounds;
    }

    /**
     * Set the bounds
     *
     * @param bounds the new bounds of the state set in a format that is usable by JavaFx's Polygon
     *        object
     */
    public void setBounds (double[] bounds) {
        myBounds = bounds;
    }

    @Override
    public Node getNode () {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Take an object's bounds and add its x and y position to the bounds to get
     * the global object bounds
     */
    public double[] findGlobalBounds () {
        double[] newBounds = myBounds.clone();
        double width = newBounds[2]-newBounds[0];
        double height = newBounds[5]-newBounds[3];
        for (int i = 0; i < newBounds.length; i += 2) {
            newBounds[i] +=
                    attributes.getNumericalAttribute(
                                                StateTags.X_POSITION)
                            .doubleValue() - width/2;
            newBounds[i + 1] +=
                    attributes.getNumericalAttribute(
                                                StateTags.Y_POSITION)
                            .doubleValue() - height/2;
        }
        return newBounds;
    }

}
