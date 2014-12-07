package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.state.gameelement.AttributeContainer;

public class AttributeArrowDisplayer extends AttributeDisplayer {

	private String URL = "resources/img/Arrow.png";
			
	public AttributeArrowDisplayer(AttributeContainer attributes,
			String numericParameterTag, double minValue, double maxValue) {
		super(attributes, numericParameterTag, minValue, maxValue);
	}

	@Override
	protected Group createDisplay() {
		Group group = new Group();
		Image img = new Image(URL);
		ImageView imgView = new ImageView(img);
		group.getChildren().add(imgView);
		return group;
	}

	@Override
	protected void updateDisplay(double attributeValue,
			double attributeMinValue, double attributeMaxValue) {
		//Nothing
	}

	
}
