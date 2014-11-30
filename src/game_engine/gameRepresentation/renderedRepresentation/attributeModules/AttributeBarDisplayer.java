package game_engine.gameRepresentation.renderedRepresentation.attributeModules;

import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


/**
 * A displaying module that displays an attribute in a bar-like form
 * 
 * @author Zach
 *
 */
public class AttributeBarDisplayer extends AttributeDisplayer {
    public final static double ATTRIBUTE_BAR_WIDTH = 30;
    public final static double ATTRIBUTE_BAR_HEIGHT = 5;
    public final static String FG_COLOR = "Red";
    public final static String BG_COLOR = "Green";
    private Rectangle myAttributeRectangle;

    public AttributeBarDisplayer (GameElement elementOfInterest,
                                  String numericParameterTag,
                                  double minValue,
                                  double maxValue) {
        super(elementOfInterest, numericParameterTag, minValue, maxValue);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Group createDisplay () {
        Group group = new Group();
        Rectangle r = new Rectangle(ATTRIBUTE_BAR_WIDTH, ATTRIBUTE_BAR_HEIGHT);
        r.setFill(Paint.valueOf("Red"));
        myAttributeRectangle = new Rectangle(ATTRIBUTE_BAR_WIDTH, ATTRIBUTE_BAR_HEIGHT);
        myAttributeRectangle.setFill(Paint.valueOf("Green"));
        group.getChildren().add(r);
        group.getChildren().add(myAttributeRectangle);
        return group;
    }

    @Override
    protected void updateDisplay (double attributeValue,
                                  double attributeMinValue,
                                  double attributeMaxValue) {
        myAttributeRectangle.setWidth(ATTRIBUTE_BAR_WIDTH * (attributeValue - attributeMinValue) /
                                      (attributeMaxValue - attributeMinValue));

    }

}
