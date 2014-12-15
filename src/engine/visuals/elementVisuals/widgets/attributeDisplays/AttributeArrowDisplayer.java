package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.state.gameelement.AttributeContainer;
import util.SaveLoadUtility;
import util.exceptions.SaveLoadException;

//This entire file is part of my masterpiece.
//Michael Deng
/**
 * Creates a selection arrow for units that are selected
 * 
 * @author Michael D.
 *
 */
public class AttributeArrowDisplayer extends AttributeDisplayer {

    private static final String imageURL = "resources/img/Arrow.png";

    /**
     * The constructor
     * 
     * @param attributes
     *            The list of attributes belonging to the game element
     * @param numericParameterTag
     *            The indicator or name of the attribute
     * @param minValue
     *            The minimum value
     * @param maxValue
     *            The maximum value
     */
    public AttributeArrowDisplayer (AttributeContainer attributes, String numericParameterTag,
            double minValue, double maxValue) {
        super(attributes, numericParameterTag, minValue, maxValue);
    }

    @Override
    protected Group createDisplay () {
        Group group = new Group();
        Image arrow = null;
        try {
            arrow = SaveLoadUtility.loadImage(imageURL);
        } catch (SaveLoadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ImageView imgView = new ImageView(arrow);
        group.getChildren().add(imgView);
        return group;
    }

    @Override
    protected void updateDisplay (double attributeValue, double attributeMinValue,
            double attributeMaxValue) {
        // nothing should happen
    }

}
