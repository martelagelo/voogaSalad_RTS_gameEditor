package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.animationEngine.AnimationEngine;
import game_engine.gameRepresentation.gameElement.DrawableGameElement;


public class RenderedDrawableGameElement {

    private DrawableGameElement state;
    private AnimationEngine animation;

    public RenderedDrawableGameElement (DrawableGameElement element) {
        // TODO: use input to set state
    }

    public void update () {
        state.update();
    }
    
    public DrawableGameElement getState() { 
        return state;
    }
}
