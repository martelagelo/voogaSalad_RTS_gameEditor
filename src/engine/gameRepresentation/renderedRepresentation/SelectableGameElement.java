package engine.gameRepresentation.renderedRepresentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.visuals.elementVisuals.Visualizer;


/**
 * A wrapper for game elements capable of being selected. Adds a "selected"
 * visual appearance to the appearance defined by the DrawableGameElement and
 * handles animations for actions resulting from being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul, John, Michael D., Zach
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState selectableState;
    private Map<String, Set<DrawableGameElement>> myInteractingElements;
    private ResourceBundle myInteractingElementTypes;

    public SelectableGameElement (DrawableGameElementState element,
                                  ResourceBundle actionTypes,
                                  ResourceBundle interactingElementTypes,
                                  Visualizer visualizer) {
        super(element, actionTypes, visualizer);
        myInteractingElementTypes = interactingElementTypes;
        initializeInteractingElementLists();

    }

    private void initializeInteractingElementLists () {
        myInteractingElements = new HashMap<>();
        for (String key : myInteractingElementTypes.keySet()) {
            String type = myInteractingElementTypes.getString(key);
            if (!myInteractingElements.containsKey(type)) {
                myInteractingElements.put(type, new HashSet<>());
            }
        }

    }

    public String getType () {
        return getTextualAttribute(StateTags.TYPE);
    }

    public void addInteractingElement (String elementType, DrawableGameElement element) {
        Set<DrawableGameElement> elements = new HashSet<>();
        Set<DrawableGameElement> oldElements = myInteractingElements.get(elementType);
        if (oldElements != null) {
            elements.addAll(myInteractingElements.get(elementType));
        }
        elements.add(element);
        myInteractingElements.put(elementType, elements);
    }

    public void addInteractingElements (String interactingElementType,
                                        List<DrawableGameElement> interactingElements) {
        for (DrawableGameElement element : interactingElements) {
            addInteractingElement(myInteractingElementTypes.getString(interactingElementType),
                                  element);
        }
    }

    public void select () {
        setNumericalAttribute(StateTags.IS_SELECTED, 1);
    }

    public void deselect () {
        setNumericalAttribute(StateTags.IS_SELECTED, 0);
    }

    @Override
    public void update () {
        updateSelfDueToCollisions();
        super.update();
        String teamColor = getTextualAttribute(StateTags.TEAM_COLOR);
        // System.out.println("Updating selectable game element: " + teamColor);
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        executeAllActions(actionTypes.getString("objective"));
    }

    public void updateSelfDueToSelection () {
        executeAllActions(actionTypes.getString("selection"));
    }

    private void updateSelfDueToVisions () {
        updateSelfDueToInteractingElementsSubset("visible", ActionType.VISION.toString());
    }

    private void updateSelfDueToCollisions () {
        updateSelfDueToInteractingElementsSubset("colliding", ActionType.COLLISION.toString());
    }

    private void updateSelfDueToInteractingElementsSubset (String elementType, String actionType) {
        // TODO: string literals still exist
        Set<DrawableGameElement> elementsOfInterest =
                myInteractingElements.get(myInteractingElementTypes.getString(elementType));
        getActionsOfType(actionType).forEachRemaining(action -> {
            for (DrawableGameElement element : elementsOfInterest) {
                ElementPair elements = new ElementPair(this, element);
                if ((Boolean) action.evaluate(elements)) { return; } // By default, only evaluate
                                                                     // one single collision action
                                                                     // per game loop refresh
            }
        });
        myInteractingElements.get(myInteractingElementTypes.getString(elementType)).clear();// After
                                                                                            // we've
                                                                                            // acted
                                                                                            // on
                                                                                            // the
                                                                                            // elements,
                                                                                            // clear
                                                                                            // the
                                                                                            // list
    }

    public void registerAsSelectableChild (Consumer<SelectableGameElementState> function) {
        function.accept(selectableState);
    }

}
