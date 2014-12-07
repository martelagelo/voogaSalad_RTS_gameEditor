package engine.elementFactories;

import javafx.beans.property.SimpleBooleanProperty;
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
    SimpleBooleanProperty enabled;

    public VisualizerFactory (AnimatorFactory animatorFactory) {
        enabled = new SimpleBooleanProperty(true);
        myAnimatorFactory = animatorFactory;
        myWidgetFactory = new AttributeDisplayerFactory();
    }

    public void setAnimationEnabled (boolean b) {
        enabled.set(b);
    }

    public Visualizer createVisualizer (DrawableGameElementState elementState) {
        Animator animator = myAnimatorFactory.createAnimator(elementState);

        Visualizer newVisualizer = new Visualizer(animator,
                                                  elementState.attributes);
        newVisualizer.setAnimateEnableProperty(enabled);

        // FOR TESTING
        // AttributeDisplayerState myAttributeDisplayerState = new
        // AttributeDisplayerState("attributeBar", StateTags.HEALTH, 0, 500);
        // newVisualizer.addWidget(myWidgetFactory.createAttributeDisplayer(myAttributeDisplayerState,elementState.attributes));

        for (AttributeDisplayerState ADS : elementState.AttributeDisplayerStates) {
            newVisualizer.addWidget(myWidgetFactory
                    .createAttributeDisplayer(ADS, elementState.attributes));
        }

        return newVisualizer;
    }
}
