package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.animationEngine.AnimationEngine;
import game_engine.gameRepresentation.gameElement.DrawableGameElement;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;

public class RenderedSelectableGameElement extends RenderedDrawableGameElement{
    
    private SelectableGameElement state;
    private AnimationEngine animation;

    public RenderedSelectableGameElement (DrawableGameElement element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public String getType () {
        return state.getType();
    }
}
