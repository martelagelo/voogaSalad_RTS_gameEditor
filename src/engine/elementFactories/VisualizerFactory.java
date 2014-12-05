package engine.elementFactories;

import model.state.gameelement.DrawableGameElementState;
import engine.visuals.elementVisuals.Visualizer;
import engine.visuals.elementVisuals.animations.Animator;

public class VisualizerFactory {


    private AnimatorFactory myAnimatorFactory;
    
    public VisualizerFactory(AnimatorFactory animatorFactory){
        myAnimatorFactory = animatorFactory;
    }
    
    public Visualizer createVisualizer(DrawableGameElementState elementState){
        Animator animator = myAnimatorFactory.createAnimator(elementState);
        // TODO : make widgets
        Visualizer newVisualizer = new Visualizer(animator);
        //newVisualizer.addWidget(widget);
        return newVisualizer; 
    }
}
