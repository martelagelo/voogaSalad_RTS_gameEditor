package engine.elementFactories;

import model.state.gameelement.Attribute;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerFactory;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerState;
import engine.visuals.elementVisuals.Visualizer;
import engine.visuals.elementVisuals.animations.Animator;

/**
 * 
 * @author Steve, Michael D.
 *
 */
public class VisualizerFactory {

	private AnimatorFactory myAnimatorFactory;
	private AttributeDisplayerFactory myWidgetFactory;

	public VisualizerFactory(AnimatorFactory animatorFactory) {
		myAnimatorFactory = animatorFactory;
	}

	public Visualizer createVisualizer(DrawableGameElementState elementState) {
		Animator animator = myAnimatorFactory.createAnimator(elementState);
		//TODO move this up. This shouldn't be remade each time
		myWidgetFactory = new AttributeDisplayerFactory();

		Visualizer newVisualizer = new Visualizer(animator,
				elementState.attributes);
//		for (Attribute<Number> a : elementState.getAttributeContainer()
//				.getNumericalAttributes()) {
//			AttributeDisplayerState myAttributeDisplayerState = new AttributeDisplayerState(
//					"attributeBar", a.getName(), 0, a.getData().doubleValue());
//			newVisualizer.addWidget(myWidgetFactory.createAttributeDisplayer(
//					myAttributeDisplayerState,
//					elementState.getAttributeContainer()));
//		}
		// TODO: make widgets from Textual Attributes
//		 for(Attribute<String> a: elementState.getAttributeContainer().getTextualAttributes()) {
//		 AttributeDisplayerState myAttributeDisplayerState =
//		 new AttributeDisplayerState("attributeBar", a.getName(), a.getData());
//		 newVisualizer.addWidget(myWidgetFactory.createAttributeDisplayer(myAttributeDisplayerState,
//		 elementState.getAttributeContainer()));
//		 }
		return newVisualizer;
	}
}
