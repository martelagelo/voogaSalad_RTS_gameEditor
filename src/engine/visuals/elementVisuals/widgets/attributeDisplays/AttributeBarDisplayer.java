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
public abstract class AttributeBarDisplayer extends AttributeDisplayer {
	public final static double ATTRIBUTE_BAR_WIDTH = 30;
	public final static double ATTRIBUTE_BAR_HEIGHT = 5;
	private Paint ForeColor;
	private Paint BackColor;
	
	private Rectangle myAttributeRectangle;

	public AttributeBarDisplayer(AttributeContainer attributes,
			String numericParameterTag, double minValue, double maxValue, String fgColor, String bgColor) {
		super(attributes, numericParameterTag, minValue, maxValue);
		ForeColor = Paint.valueOf(fgColor);
		BackColor = Paint.valueOf(bgColor);
	}

	@Override
	protected Group createDisplay() {
		Group bar = new Group();
		Group group = new Group();
		Rectangle border = new Rectangle(ATTRIBUTE_BAR_WIDTH + 4,
				ATTRIBUTE_BAR_HEIGHT + 4);
		border.setFill(Color.BLACK);
		Rectangle r = new Rectangle(ATTRIBUTE_BAR_WIDTH, ATTRIBUTE_BAR_HEIGHT);
		r.setFill(BackColor);
		myAttributeRectangle = new Rectangle(ATTRIBUTE_BAR_WIDTH,
				ATTRIBUTE_BAR_HEIGHT);
		myAttributeRectangle.setFill(ForeColor);
		group.getChildren().add(border);
		bar.getChildren().add(r);
		bar.getChildren().add(myAttributeRectangle);
		bar.setLayoutX(2);
		bar.setLayoutY(2);
		group.getChildren().add(bar);
		return group;
	}

	@Override
	protected void updateDisplay(double attributeValue,
			double attributeMinValue, double attributeMaxValue) {
		myAttributeRectangle.setWidth(ATTRIBUTE_BAR_WIDTH
				* (attributeValue - attributeMinValue)
				/ (attributeMaxValue - attributeMinValue));

	}

}
