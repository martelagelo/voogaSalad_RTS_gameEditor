package model.state.gameelement;
//THIS ENTIRE CLASS IS PART OF MY MASTERPIECE
import model.state.gameelement.traits.Boundable;
import engine.visuals.elementVisuals.animations.AnimatorState;


/**
 * This GameElement is drawable but not necessarily selectable - examples
 * include terrain. These elements must have a bounding box.
 *
 * @author Steve, Jonathan, Rahul, Zach, Michael D.
 *
 */
public class DrawableGameElementState extends GameElementState implements
        Boundable {

    private static final long serialVersionUID = -2449775294910832264L;

    public AnimatorState myAnimatorState;
    private double[] myBounds;

    /**
     * Create a drawable game element at a given x and y position.
     *
     * @param xPosition
     *        the x position of the element
     * @param yPosition
     *        the y position of the element
     */
    public DrawableGameElementState (Number xPosition, Number yPosition,
                                     AnimatorState animatorState) {
        super();
        myAnimatorState = animatorState;

        myBounds = new double[8];
        myAttributes.setNumericalAttribute(StateTags.X_POSITION.getValue(), xPosition);
        myAttributes.setNumericalAttribute(StateTags.Y_POSITION.getValue(), yPosition);

    }


    @Override
    public double[] getBounds () {
        return myBounds;
    }

    /**
     * Set the bounds
     *
     * @param bounds
     *        the new bounds of the state set in a format that is usable by
     *        JavaFx's Polygon object
     */
    public void setBounds (double[] bounds) {
        myBounds = bounds;
    }

    /**
     * Take an object's bounds and add its x and y position to the bounds to get
     * the global object bounds
     */
    public double[] findGlobalBounds () {
        double[] newBounds = myBounds.clone();
        double width = newBounds[2] - newBounds[0];
        double height = newBounds[5] - newBounds[3];
        for (int i = 0; i < newBounds.length; i += 2) {
            newBounds[i] +=
                    myAttributes.getNumericalAttribute(
                                                     StateTags.X_POSITION.getValue()).doubleValue()
                            - width / 2;
            newBounds[i + 1] +=
                    myAttributes.getNumericalAttribute(
                                                     StateTags.Y_POSITION.getValue()).doubleValue()
                            - height / 2;
        }
        return newBounds;
    }

}
