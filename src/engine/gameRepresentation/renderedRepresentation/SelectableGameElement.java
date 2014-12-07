package engine.gameRepresentation.renderedRepresentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import model.state.gameelement.DrawableGameElementState;
import model.state.gameelement.SelectableGameElementState;
import model.state.gameelement.StateTags;
import engine.computers.objectClassifications.InteractingElementType;
import engine.gameRepresentation.evaluatables.ElementPair;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;
import engine.visuals.elementVisuals.Visualizer;


/**
 * A wrapper for game elements capable of being selected. Adds a "selected"
 * visual appearance to the appearance defined by the DrawableGameElement and
 * handles animations for actions resulting from being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul, John, Michael D., Zach, Stanley
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState selectableState;

    private Map<InteractingElementType, Set<DrawableGameElement>> myInteractingElements;
    // The element that is currently being focused on by the element
    private SelectableGameElement myFocusedElement;

    public SelectableGameElement (SelectableGameElementState element,
                                  Visualizer visualizer) {
        super(element, visualizer);
        selectableState = element;
        initializeInteractingElementLists();
    }

    public void setFocusedElement (SelectableGameElement element) {
        myFocusedElement = element;
    }

    public void clearFocusedElement () {
        myFocusedElement = null;
    }

    private void initializeInteractingElementLists () {
        myInteractingElements = new HashMap<>();
        for (InteractingElementType type : InteractingElementType.values()) {
            if (!myInteractingElements.containsKey(type)) {
                myInteractingElements.put(type, new HashSet<>());
            }
        }

    }

    public String getType () {
        return getTextualAttribute(StateTags.TYPE.getValue());
    }

    public void addInteractingElement (InteractingElementType elementType,
                                       DrawableGameElement element) {
        Set<DrawableGameElement> elements = new HashSet<>();
        Set<DrawableGameElement> oldElements = myInteractingElements.get(elementType);
        if (oldElements != null) {
            elements.addAll(myInteractingElements.get(elementType));
        }
        elements.add(element);
        myInteractingElements.put(elementType, elements);
    }

    public void addInteractingElements (InteractingElementType interactingElementType,
                                        List<DrawableGameElement> interactingElements) {
        for (DrawableGameElement element : interactingElements) {
            addInteractingElement(interactingElementType,element);
        }
    }

    public void select () {
        setNumericalAttribute(StateTags.IS_SELECTED.getValue(), 1);
    }

    public void deselect () {
        setNumericalAttribute(StateTags.IS_SELECTED.getValue(), 0);
    }

    @Override
    public void update () {
        updateSelfDueToCollisions();
        if (myFocusedElement != null) {
            updateSelfDueToFocusedElement();
        }
        super.update();
        String teamColor = getTextualAttribute(StateTags.TEAM_COLOR.getValue());
        // System.out.println("Updating selectable game element: " + teamColor);
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        executeAllActions(ActionType.OBJECTIVE);
    }

    private void updateSelfDueToFocusedElement () {
        executeAllActions(ActionType.FOCUSED, new ElementPair(this, myFocusedElement));
    }

    public void updateSelfDueToSelection () {
        executeAllActions(ActionType.SELECTION);
    }

    private void updateSelfDueToVisions () {
        updateSelfDueToInteractingElementsSubset(InteractingElementType.VISIBLE, ActionType.VISION);
    }

    private void updateSelfDueToCollisions () {
        updateSelfDueToInteractingElementsSubset(InteractingElementType.COLLIDING,
                                                 ActionType.COLLISION);
    }

    private void updateSelfDueToInteractingElementsSubset (InteractingElementType elementType,
                                                           ActionType actionType) {
        // TODO: string literals still exist
        Set<DrawableGameElement> elementsOfInterest = myInteractingElements.get(elementType);
        getActionsOfType(actionType).forEachRemaining(action -> {
            for (DrawableGameElement element : elementsOfInterest) {
                ElementPair elements = new ElementPair(this, element);
                if ((Boolean) action.evaluate(elements)) { return; }
                // By default, only evaluate one single collision action per game loop refresh
            }
        });

        myInteractingElements.get(elementType).clear();
        // After we've acted on the elements, clear the list

    }

    public void registerAsSelectableChild (Consumer<SelectableGameElementState> function) {
        function.accept(selectableState);
    }

    public void executeAllButtonActions () {
        this.executeAllActions(ActionType.BUTTON, new ElementPair(this, this));
    }

    public Map<Integer, String> getAbilityDescriptionMap (int numAttributes) {
        Map<Integer, String> descriptionMap = new HashMap<>();
        for (int i = 1; i <= numAttributes; i++) {
            String description = this.getTextualAttribute(StateTags.ATTRIBUTE_DESCRIPTION.getValue() + i);
            descriptionMap.put(i, description);
        }

        return descriptionMap;
    }

}
