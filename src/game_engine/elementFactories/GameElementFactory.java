package game_engine.elementFactories;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.EvaluatableFactory;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import gamemodel.GameUniverse;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Factory that takes in GameElementStates and generates their JavaFX representations as well as
 * their Evaluatables, then packs it all into the rendered wrapper used in the engine.
 * 
 * @author Steve
 *
 */
public class GameElementFactory {

    public static final String ACTION_TYPE_LOCATION = "resources.properties.ActionTypes";
    public static final String INTERACTING_ELEMENT_TYPE_LOCATION =
            "resources.properties.InteractingElementTypes";

    private GameUniverse myUniverse;
    private EvaluatableFactory myFactory;

    private ResourceBundle actionTypes;
    private ResourceBundle interactingElementTypes;

    public GameElementFactory (GameUniverse universe, EvaluatableFactory factory) {
        myUniverse = universe;
        myFactory = factory;
        actionTypes = ResourceBundle.getBundle(ACTION_TYPE_LOCATION);
        interactingElementTypes = ResourceBundle.getBundle(INTERACTING_ELEMENT_TYPE_LOCATION);
    }

    public GameElement createGameElement (GameElementState state) {
        GameElement element = new GameElement(state, generateActions(state), actionTypes);
        return element;
    }

    public DrawableGameElement createDrawableGameElement (DrawableGameElementState state) {
        DrawableGameElement element =
                new DrawableGameElement(state, generateActions(state), actionTypes);
        return element;
    }

    public SelectableGameElement createSelectableGameElement (SelectableGameElementState state) {
        SelectableGameElement element =
                new SelectableGameElement(state, generateActions(state), actionTypes,
                                          interactingElementTypes);
        return element;
    }

    public SelectableGameElement createSelectableGameElement (String elementTag) {
        return createSelectableGameElement(myUniverse.getSelectableElementState(elementTag));
    }

    private Map<String, List<Evaluatable<?>>> generateActions (GameElementState state) {
        return myFactory.generateEvaluatables(state.getActions());
    }
}
