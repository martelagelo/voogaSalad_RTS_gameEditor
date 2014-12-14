package engine.elementFactories;

import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.traits.AttributeDisplayerState;
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

    public VisualizerFactory (AnimatorFactory animatorFactory) {
        myAnimatorFactory = animatorFactory;
        myWidgetFactory = new AttributeDisplayerFactory();
    }

    public Visualizer createVisualizer (DrawableGameElementState elementState) {
        Animator animator = myAnimatorFactory.createAnimator(elementState);

        Visualizer newVisualizer = new Visualizer(animator, elementState.myAttributes);
        for (AttributeDisplayerState ADS : elementState.myAttributeDisplayerStates) {
            newVisualizer.addWidget(myWidgetFactory.createAttributeDisplayer(ADS,
                    elementState.myAttributes));
        }

        return newVisualizer;
    }
}
