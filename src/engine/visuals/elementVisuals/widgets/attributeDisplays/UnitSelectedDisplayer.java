package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import model.state.gameelement.AttributeContainer;

/**
 * A displaying module that displays whether the unit is selected with a
 * triangle
 *
 * @author Stanley
 *
 */

public class UnitSelectedDisplayer extends AttributeDisplayer {

    private Polygon mySelectionTriangle;
    public final static String TRIANGLE_COLOR = "Blue";
    public final static double TRIANGLE_SIDE_LENGTH = 25;

    public UnitSelectedDisplayer (AttributeContainer attributes, String numericParameterTag,
            double minValue, double maxValue) {
        super(attributes, numericParameterTag, minValue, maxValue);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Group createDisplay () {
        Group group = new Group();
        mySelectionTriangle = new Polygon(TRIANGLE_SIDE_LENGTH);
        // TODO: Shitty placement of triangle, do this in a better way
        mySelectionTriangle.getPoints().addAll(
                new Double[] { 0.0, 0.0, TRIANGLE_SIDE_LENGTH, 0.0, 0.0, TRIANGLE_SIDE_LENGTH });
        mySelectionTriangle.setFill(Paint.valueOf(TRIANGLE_COLOR));
        return group;
    }

    @Override
    protected void updateDisplay (double attributeValue, double attributeMinValue,
            double attributeMaxValue) {
        if (attributeValue == 1) {
            super.myGroup.getChildren().add(mySelectionTriangle);
        } else {
            super.myGroup.getChildren().remove(mySelectionTriangle);
        }

    }

}