package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.state.gameelement.AttributeContainer;

/**
 * A displaying module that displays an attribute in a bar-like form
 *
 * @author Zach, Steve
 *
 */
public class AttributeBarDisplayer extends AttributeDisplayer {
    public final static double ATTRIBUTE_BAR_WIDTH = 30;
    public final static double ATTRIBUTE_BAR_HEIGHT = 5;

    public final static String FG_COLOR = "Red";
    public final static String BG_COLOR = "Green";

    private Rectangle myAttributeRectangle;

    public AttributeBarDisplayer (AttributeContainer attributes, String numericParameterTag,
            double minValue, double maxValue) {
        super(attributes, numericParameterTag, minValue, maxValue);

    }

    @Override
    protected Group createDisplay () {
        Group bar = new Group();
        Group group = new Group();
        Rectangle border = new Rectangle(ATTRIBUTE_BAR_WIDTH + 4, ATTRIBUTE_BAR_HEIGHT + 4);
        border.setFill(Color.BLACK);
        Rectangle r = new Rectangle(ATTRIBUTE_BAR_WIDTH, ATTRIBUTE_BAR_HEIGHT);
        r.setFill(Paint.valueOf(FG_COLOR));
        myAttributeRectangle = new Rectangle(ATTRIBUTE_BAR_WIDTH, ATTRIBUTE_BAR_HEIGHT);
        myAttributeRectangle.setFill(Paint.valueOf(BG_COLOR));
        group.getChildren().add(border);
        bar.getChildren().add(r);
        bar.getChildren().add(myAttributeRectangle);
        bar.setLayoutX(2);
        bar.setLayoutY(2);
        group.getChildren().add(bar);
        return group;
    }

    @Override
    protected void updateDisplay (double attributeValue, double attributeMinValue,
            double attributeMaxValue) {
        myAttributeRectangle.setWidth(ATTRIBUTE_BAR_WIDTH * (attributeValue - attributeMinValue)
                / (attributeMaxValue - attributeMinValue));

    }

}
