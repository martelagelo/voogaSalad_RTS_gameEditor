package engine.elementFactories;

import model.MainModel;
import model.sprite.SpriteImageContainer;
import model.state.gameelement.AttributeContainer;
import model.state.gameelement.DrawableGameElementState;
import engine.visuals.elementVisuals.animations.Animator;
import engine.visuals.elementVisuals.animations.AnimatorState;

public class AnimatorFactory {

    private MainModel myModel;

    public AnimatorFactory (MainModel model) {
        myModel = model;
    }

    public Animator createAnimator (DrawableGameElementState elementState) {
        AttributeContainer attributes = elementState.attributes;
        AnimatorState animatorState = elementState.myAnimatorState;
        SpriteImageContainer images = myModel.fetchImageContainer(animatorState.getImageTag());
        return new Animator(images, animatorState, attributes);
    }

}
