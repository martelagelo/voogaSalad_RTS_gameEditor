package game_engine.visuals.elementVisuals.widgets;

import java.util.function.Consumer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.AttributeContainer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.traits.Updatable;
import javafx.scene.Group;
import javafx.scene.Node;


/**
 * A parent class for a basic visual element that displays an attribute of an element and updates
 * its visual representation.
 * 
 * @author Zach
 *
 */
public abstract class AttributeDisplayer implements Updatable {

    private Group myGroup;
    private AttributeContainer attributesOfInterest;
    private String myNumericParameterTag;
    private double myMinValue;
    private double myMaxValue;

    /**
     * Create an attribute displayer
     * 
     * @param attributes the element whose attribute will be reflected
     * @param numericParameterTag the tag of the numeric parameter to display
     * @param minValue the min value of the parameter
     * @param maxValue the max value of the parameter
     */
    public AttributeDisplayer (AttributeContainer attributes,
                               String numericParameterTag,
                               double minValue,
                               double maxValue) {
        myGroup = createDisplay();
        attributesOfInterest = attributes;
        myNumericParameterTag = numericParameterTag;
        myMinValue = minValue;
        myMaxValue = maxValue;
    }
    
    public void registerNode (Consumer<Node> registerFunction) {
        registerFunction.accept(myGroup);
    }

    @Override
    public boolean update () {
        updateDisplay(attributesOfInterest.getNumericalAttribute(myNumericParameterTag)
                .doubleValue(), myMinValue, myMaxValue);
        return true;
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
