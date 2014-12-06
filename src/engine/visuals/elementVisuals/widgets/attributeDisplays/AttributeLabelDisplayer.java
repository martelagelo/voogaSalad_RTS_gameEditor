package engine.visuals.elementVisuals.widgets.attributeDisplays;

import model.state.gameelement.AttributeContainer;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Michael D.
 *
 */
public class AttributeLabelDisplayer extends AttributeDisplayer{

	public final static double ATTRIBUTE_BAR_WIDTH = 30;
	public final static double ATTRIBUTE_BAR_HEIGHT = 5;

	private Rectangle myAttributeRectangle;

	public AttributeLabelDisplayer(AttributeContainer attributes,
			String numericParameterTag, String content) {
		super(attributes, numericParameterTag, content);
	}

	@Override
	protected Group createDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateDisplay(double attributeValue,
			double attributeMinValue, double attributeMaxValue) {
		// TODO Auto-generated method stub
		
	}

}
