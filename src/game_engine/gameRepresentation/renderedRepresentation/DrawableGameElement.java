package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.animationEngine.AnimationEngine;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;


public class DrawableGameElement {

    private DrawableGameElementState state;
    private AnimationEngine animation;

    public DrawableGameElement (DrawableGameElementState element) {
        // TODO: use input to set state
    }

    public void update () {
        state.update();
    }

    public DrawableGameElementState getState () {
        return state;
    }
}
