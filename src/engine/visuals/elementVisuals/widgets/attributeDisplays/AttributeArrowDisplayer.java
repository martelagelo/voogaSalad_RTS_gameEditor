package engine.visuals.elementVisuals.widgets.attributeDisplays;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exceptions.SaveLoadException;
import model.state.gameelement.AttributeContainer;
import util.SaveLoadUtility;

public class AttributeArrowDisplayer extends AttributeDisplayer {

	private static final String URL = "resources/img/Arrow.png";
			
	public AttributeArrowDisplayer(AttributeContainer attributes,
			String numericParameterTag, double minValue, double maxValue) {
		super(attributes, numericParameterTag, minValue, maxValue);
	}

	@Override
	protected Group createDisplay() {
		Group group = new Group();
		Image arrow = null;
		try {
			arrow = SaveLoadUtility.loadImage(URL);
		} catch (SaveLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageView imgView = new ImageView(arrow);
		group.getChildren().add(imgView);
		return group;
	}

	@Override
	protected void updateDisplay(double attributeValue,
			double attributeMinValue, double attributeMaxValue) {
		//nothing should happen
	}

	
}
