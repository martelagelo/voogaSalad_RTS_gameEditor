package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.ElementPair;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.visuals.elementVisuals.Visualizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;


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
                                  Map<String, List<Evaluatable<?>>> conditionActionPairs,
                                  ResourceBundle actionTypes,
                                  ResourceBundle interactingElementTypes,
                                  Visualizer visualizer) {
        super(element, conditionActionPairs, actionTypes, visualizer);
        myInteractingElementTypes = interactingElementTypes;
        initializeInteractingElementLists();

        // TODO: remove this shit

        setNumericalAttribute(StateTags.IS_SELECTED, 0);
        setNumericalAttribute(StateTags.X_VELOCITY, 0);
        setNumericalAttribute(StateTags.Y_VELOCITY, 0);
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
    
    public void deselect() {
        setNumericalAttribute(StateTags.IS_SELECTED, 0);
    }

    @Override
    public void update () {
        super.update();
        updateSelfDueToCollisions();
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
        updateSelfDueToInteractingElementsSubset("visible", "vision");
    }

    private void updateSelfDueToCollisions () {
        updateSelfDueToInteractingElementsSubset("colliding", "collision");
    }

    private void updateSelfDueToInteractingElementsSubset (String elementType, String actionType) {
        // TODO: string literals still exist
        Set<DrawableGameElement> elementsOfInterest =
                myInteractingElements.get(myInteractingElementTypes.getString(elementType));
        getActionsOfType(actionType).forEachRemaining(action -> {
            for (DrawableGameElement element : elementsOfInterest) {
                ElementPair elements = new ElementPair(this, element);
                if ((Boolean) action.evaluate(elements)) { return; } // TODO do something?? or
                                                                     // continue? what does a true
                                                                     // return statement mean?
            }
        });
    }
}
