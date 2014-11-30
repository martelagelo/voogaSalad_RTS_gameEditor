package game_engine.elementFactories;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.elementVisuals.Visualizer;
import game_engine.visuals.elementVisuals.animations.Animator;

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
