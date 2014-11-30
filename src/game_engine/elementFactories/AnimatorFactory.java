package game_engine.elementFactories;

import game_engine.gameRepresentation.stateRepresentation.AnimatorState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.AttributeContainer;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.elementVisuals.animations.Animator;
import game_engine.visuals.elementVisuals.animations.SpriteImageContainer;
import gamemodel.MainModel;

public class AnimatorFactory {
    
    private MainModel myModel;
    
    public AnimatorFactory (MainModel model) {
        myModel = model;
    }
    
    public Animator createAnimator(DrawableGameElementState elementState){
        AttributeContainer attributes = elementState.attributes;
        AnimatorState animatorState = elementState.myAnimatorState;
        SpriteImageContainer images = myModel.fetchImageContainer(animatorState.getImageTag());
        return new Animator(images, animatorState, attributes);
    }

}
