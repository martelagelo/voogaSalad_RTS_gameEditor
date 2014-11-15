package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.gameElement.DrawableGameElement;
import game_engine.visuals.AnimationPlayer;


public class RenderedDrawableGameElement {

    private DrawableGameElement state;
    private AnimationPlayer animation;

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
