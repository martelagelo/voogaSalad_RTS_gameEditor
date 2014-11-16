package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.AnimationPlayer;


public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState state;
    private AnimationPlayer animation;
    private boolean selected;

    public SelectableGameElement (DrawableGameElementState element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public String getType () {
        return state.getType();
    }
    
    public void select(boolean select){
        selected = select;
        // TODO implement
    }
}
