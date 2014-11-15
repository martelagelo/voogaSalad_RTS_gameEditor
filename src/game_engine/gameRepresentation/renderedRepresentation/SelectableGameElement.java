package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.animationEngine.AnimationEngine;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;


public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState state;
    private AnimationEngine animation;

    public SelectableGameElement (DrawableGameElementState element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public String getType () {
        return state.getType();
    }
}
