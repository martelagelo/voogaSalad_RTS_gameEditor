package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.state.gameelement.AttributeContainer;

public class HealthBarDisplayer extends AttributeBarDisplayer {

	public final static String FG_COLOR = "BLUE";
	public final static String BG_COLOR = "YELLOW";
	
	public HealthBarDisplayer(AttributeContainer attributes,
			String numericParameterTag, double minValue, double maxValue) {
		super(attributes, numericParameterTag, minValue, maxValue, FG_COLOR, BG_COLOR);
	}
	
	
}
