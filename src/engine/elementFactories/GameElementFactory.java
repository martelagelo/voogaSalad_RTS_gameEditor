package engine.elementFactories;

import java.util.List;
import java.util.Map.Entry;
import model.GameUniverse;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.GameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.Evaluatable;
import engine.gameRepresentation.evaluatables.actions.ActionFactory;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.gameRepresentation.evaluatables.evaluators.FalseEvaluator;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.GameElement;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.visuals.elementVisuals.Visualizer;


/**
 * Factory that takes in GameElementStates and generates their JavaFX representations as well as
 * their Evaluatables, then packs it all into the rendered wrapper used in the engine.
 * 
 * @author Steve, Stanley
 *
 */
public class GameElementFactory {
    private GameUniverse myUniverse;
    private ActionFactory myActionFactory;
    private VisualizerFactory myVisualizerFactory;

    public GameElementFactory (GameUniverse universe,
                               ActionFactory actionFactory,
                               VisualizerFactory visualizerFactory) {
        myUniverse = universe;
        myActionFactory = actionFactory;
        myVisualizerFactory = visualizerFactory;
    }

    public GameElement createGameElement (String elementType, double x, double y) {
        GameElementState state = myUniverse.getGameElementState(elementType);
        GameElement newElement = createGameElement(state);
        newElement.setPosition(x, y);
        return newElement;
    }

    public GameElement createGameElement (GameElementState state) {
        GameElement element = new GameElement(state);
        generateActions(element, state);
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
                new DrawableGameElement(state,
                                        generateVisualizer(state));
        generateActions(element, state);
        return element;
    }

    public SelectableGameElement createSelectableGameElement (String elementType,
                                                              double x,
                                                              double y,
                                                              String color) {
        SelectableGameElementState state = myUniverse.getSelectableGameElementState(elementType);
        state.attributes.setTextualAttribute(StateTags.TEAM_COLOR, color);
        SelectableGameElement newElement = createSelectableGameElement(state);
        newElement.setPosition(x, y);
        return newElement;
    }

    public SelectableGameElement createSelectableGameElement (SelectableGameElementState state) {
        SelectableGameElement element =
                new SelectableGameElement(state,generateVisualizer(state));
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
        for (Entry<ActionType, List<ActionWrapper>> entry : state.getActions().entrySet()) {
            entry.getValue().forEach(actionWrapper -> {
                Evaluatable<?> action;
                try {
                    action = myActionFactory.createAction(actionWrapper);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    action = new FalseEvaluator();
                }
                element.addAction(entry.getKey(), action);
            });
        }
    }

    private Visualizer generateVisualizer (DrawableGameElementState elementState) {
        return myVisualizerFactory.createVisualizer(elementState);
    }
}
