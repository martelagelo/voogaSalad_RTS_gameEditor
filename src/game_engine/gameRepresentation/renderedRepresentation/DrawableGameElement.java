package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.visuals.AnimationPlayer;
public class DrawableGameElement {

    private DrawableGameElementState state;
    private AnimationPlayer animation;

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
