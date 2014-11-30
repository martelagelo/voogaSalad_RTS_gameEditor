package game_engine.elementFactories;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.EvaluatableFactory;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.elementVisuals.Visualizer;
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
    private EvaluatableFactory myEvaluatableFactory;
    private VisualizerFactory myVisualizerFactory;

    private ResourceBundle actionTypes;
    private ResourceBundle interactingElementTypes;

    public GameElementFactory (GameUniverse universe,
                               EvaluatableFactory evaluatableFactory,
                               VisualizerFactory visualizerFactory) {
        myUniverse = universe;
        myEvaluatableFactory = evaluatableFactory;
        myVisualizerFactory = visualizerFactory;
        actionTypes = ResourceBundle.getBundle(ACTION_TYPE_LOCATION);
        interactingElementTypes = ResourceBundle.getBundle(INTERACTING_ELEMENT_TYPE_LOCATION);
    }

    public GameElement createGameElement (String elementType, double x, double y) {
        GameElementState state = myUniverse.getGameElementState(elementType);
        GameElement newElement = createGameElement(state);
        newElement.setPosition(x, y);
        return newElement;
    }

    public GameElement createGameElement (GameElementState state) {
        GameElement element = new GameElement(state, generateActions(state), actionTypes);
        return element;
    }

    public DrawableGameElement createDrawableGameElement (String elementType, double x, double y) {
        DrawableGameElementState state = myUniverse.getDrawableGameElementState(elementType);
        DrawableGameElement newElement = createDrawableGameElement(state);
        newElement.setPosition(x, y);
        return newElement;
    }

    public DrawableGameElement createDrawableGameElement (DrawableGameElementState state) {
        DrawableGameElement element =
                new DrawableGameElement(state, generateActions(state), actionTypes,
                                        generateVisualizer(state));
        return element;
    }

    public SelectableGameElement createSelectableGameElement (String elementType, double x, double y) {
        SelectableGameElementState state = myUniverse.getSelectableGameElementState(elementType);
        SelectableGameElement newElement = createSelectableGameElement(state);
        newElement.setPosition(x, y);
        return newElement;
    }

    public SelectableGameElement createSelectableGameElement (SelectableGameElementState state) {
        SelectableGameElement element =
                new SelectableGameElement(state, generateActions(state), actionTypes,
                                          interactingElementTypes, generateVisualizer(state));
        return element;
    }

    private Map<String, List<Evaluatable<?>>> generateActions (GameElementState state) {
        return myEvaluatableFactory.generateEvaluatables(state.getActions());
    }

    private Visualizer generateVisualizer (DrawableGameElementState elementState) {
        return myVisualizerFactory.createVisualizer(elementState);
    }
}
