package engine.elementFactories;

import java.util.ResourceBundle;
import model.GameUniverse;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.ActionFactory;
import engine.gameRepresentation.evaluatables.evaluators.FalseEvaluator;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.visuals.elementVisuals.Visualizer;


/**
 * Factory that takes in GameElementStates and generates their JavaFX representations as well as
 * their Evaluatables, then packs it all into the rendered wrapper used in the engine.
 * 
 * @author Steve
 *
 */
public class GameElementFactory {

    public static final String ACTION_TYPE_LOCATION = "resources.properties.engine.ActionTypes";
    public static final String INTERACTING_ELEMENT_TYPE_LOCATION =
            "resources.properties.engine.InteractingElementTypes";

    private GameUniverse myUniverse;
    private ActionFactory myActionFactory;
    private VisualizerFactory myVisualizerFactory;

    private ResourceBundle actionTypes;
    private ResourceBundle interactingElementTypes;

    public GameElementFactory (GameUniverse universe,
                               ActionFactory actionFactory,
                               VisualizerFactory visualizerFactory) {
        myUniverse = universe;
        myActionFactory = actionFactory;
        myVisualizerFactory = visualizerFactory;
        actionTypes = ResourceBundle.getBundle(ACTION_TYPE_LOCATION);
        interactingElementTypes = ResourceBundle.getBundle(INTERACTING_ELEMENT_TYPE_LOCATION);
    }

    public GameElement createGameElement (String elementType, double x, double y) {
        GameElementState state = myUniverse.getGameElementState(elementType);
        GameElement newElement = createGameElement(state);
        newElement.setHeading(x, y);
        return newElement;
    }

    public GameElement createGameElement (GameElementState state) {
        GameElement element = new GameElement(state, actionTypes);
        generateActions(element, state);
        return element;
    }

    public DrawableGameElement createDrawableGameElement (String elementType, double x, double y) {
        DrawableGameElementState state = myUniverse.getDrawableGameElementState(elementType);
        DrawableGameElement newElement = createDrawableGameElement(state);
        newElement.setHeading(x, y);
        return newElement;
    }

    public DrawableGameElement createDrawableGameElement (DrawableGameElementState state) {
        DrawableGameElement element =
                new DrawableGameElement(state, actionTypes,
                                        generateVisualizer(state));
        generateActions(element, state);
        return element;
    }

    public SelectableGameElement createSelectableGameElement (String elementType, double x, double y) {
        SelectableGameElementState state = myUniverse.getSelectableGameElementState(elementType);
        SelectableGameElement newElement = createSelectableGameElement(state);
        newElement.setHeading(x, y);
        return newElement;
    }

    public SelectableGameElement createSelectableGameElement (SelectableGameElementState state) {
        SelectableGameElement element =
                new SelectableGameElement(state, actionTypes,
                                          interactingElementTypes, generateVisualizer(state));
        generateActions(element, state);
        return element;
    }

    /**
     * Iterate through the actions within an element's state and instantiate all the element's
     * actions
     * 
     * @param element the game element
     * @param state the game element's state
     */
    private void generateActions (GameElement element, GameElementState state) {
        for (String key : state.getActions().keySet()) {
            state.getActions().get(key).forEach(actionWrapper -> {
                Evaluatable<?> action;
                try {
                    action = myActionFactory.createAction(actionWrapper);
                }
                catch (Exception e) {
                    //System.out.println("Error in action creation");
                    e.printStackTrace();
                    action = new FalseEvaluator();
                }
                element.addAction(key, action);
            });
        }
    }

    private Visualizer generateVisualizer (DrawableGameElementState elementState) {
        return myVisualizerFactory.createVisualizer(elementState);
    }
}
