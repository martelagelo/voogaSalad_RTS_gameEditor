package game_engine.gameRepresentation.renderedRepresentation.attributeModules;

import javafx.scene.Group;
import javafx.scene.Node;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;
import game_engine.visuals.Displayable;


/**
 * A parent class for a basic visual element that displays an attribute of an element and updates
 * its visual representation.
 * 
 * @author Zach
 *
 */
public abstract class AttributeDisplayer implements Displayable, Updatable {

    private Group myGroup;
    private GameElement myElementOfInterest;
    private String myNumericParameterTag;
    private double myMinValue;
    private double myMaxValue;

    /**
     * Create an attribute displayer
     * 
     * @param elementOfInterest the element whose attribute will be reflected
     * @param numericParameterTag the tag of the numeric parameter to display
     * @param minValue the min value of the parameter
     * @param maxValue the max value of the parameter
     */
    public AttributeDisplayer (GameElement elementOfInterest,
                               String numericParameterTag,
                               double minValue,
                               double maxValue) {
        myGroup = createDisplay();
        myElementOfInterest = elementOfInterest;
        myNumericParameterTag = numericParameterTag;
        myMinValue = minValue;
        myMaxValue = maxValue;
    }

    @Override
    public boolean update () {
        updateDisplay(myElementOfInterest.getNumericAttribute(myNumericParameterTag).doubleValue(),
                      myMinValue,
                      myMaxValue);
        return true;
    }

    @Override
    public Node getNode () {
        return myGroup;
    }

    /**
     * Create the display for the graphical element
     * 
     * @return a group containing the visual representation for the element
     */
    protected abstract Group createDisplay ();

    /**
     * Update the display of the attribute displayer
     * 
     * @param attributeValue the attribute's value
     * @param attributeMinValue the minimum value of the attribute
     * @param attributeMaxValue the maximum value of the attribute
     */
    protected abstract void updateDisplay (double attributeValue,
                                           double attributeMinValue,
                                           double attributeMaxValue);
}
