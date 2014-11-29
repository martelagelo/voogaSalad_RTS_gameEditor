package game_engine.elementFactories;

import game_engine.gameRepresentation.evaluatables.EvaluatableFactory;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import gamemodel.GameUniverse;


/**
 * Factory that takes in GameElementStates and generates their JavaFX representations as well as
 * their Evaluatables, then packs it all into the rendered wrapper used in the engine.
 * 
 * @author Steve
 *
 */
public class GameElementFactory {

    private GameUniverse myUniverse;
    private EvaluatableFactory myFactory;

    public GameElementFactory (GameUniverse universe, EvaluatableFactory factory) {
        myUniverse = universe;
        myFactory = factory;
    }

    public GameElement createGameElement (GameElementState state) {
        GameElement element =
                new GameElement(state, myFactory.generateEvaluatables(state.getActions()));
        return element;
    }

    public DrawableGameElement createDrawableGameElement (DrawableGameElementState state) {
        DrawableGameElement element =
                new DrawableGameElement(state, myFactory.generateEvaluatables(state.getActions()));
        return element;
    }

    public SelectableGameElement createSelectableGameElement (SelectableGameElementState state) {
        SelectableGameElement element =
                new SelectableGameElement(state, myFactory.generateEvaluatables(state.getActions()));
        return element;
    }

    public SelectableGameElement createSelectableGameElement (String elementTag) {
        return createSelectableGameElement(myUniverse.getSelectableElementState(elementTag));
    }
}

// TODO: re-evaluate the need for this class
