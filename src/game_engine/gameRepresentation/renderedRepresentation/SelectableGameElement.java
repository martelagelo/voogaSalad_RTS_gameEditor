package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import java.util.Map.Entry;


/**
 * A wrapper for game elements capable of being selected. Adds a "selected" visual appearance to the
 * appearance defined by the DrawableGameElement and handles animations for actions resulting from
 * being selected.
 *
 * @author Jonathan , Steve, Nishad, Rahul
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState myState;
    private boolean isSelected;

    /**
     * @see DrawableGameElementState
     */
    public SelectableGameElement (DrawableGameElementState element) {
        super(element);
    }

    /**
     * @return the type of the element
     */
    public String getType () {
        return myState.getType();
    }

    /**
     * Select the element
     *
     * @param select a boolean indicating whether the object should be selected
     */
    public void select (boolean select) {
        isSelected = select;
        myAnimation.select(select);
    }

    /**
     * Update the object due to internal influences then update the object due to collisions,
     * visible objects, and the current objective of the object
     */
    @Override
    public void update () {
        super.update();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        getApplicableConditionActionPairs("SelfCondition");
    }

    private void updateSelfDueToVisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("VisionCondition",
                "VisibleElements");
    }

    private void updateSelfDueToCollisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("CollisionCondition",
                "CollidingElements");
    }

    // TODO FIX THIS SHIT
    private void evaluateConditionActionPairsOnInteractingElementsSubset (String conditionActionPairIdentifier,
                                                                          String elementIdentifier) {

        // List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
        // getApplicableConditionActionPairs(conditionActionPairIdentifier);
        // if (myState.getInteractingElements().containsKey(elementIdentifier)) {
        // for (DrawableGameElementState element : myState.getInteractingElements()
        // .get(elementIdentifier)) {
        // List<GameElementState> immediatelyInteractingElements =
        // new ArrayList<GameElementState>();
        // for (Entry<Evaluatable, Action> conditionActionPair : applicableConditionActionPairs) {
        // if (conditionActionPair.getKey().evaluate(this, element)) {
        // conditionActionPair.getValue().doAction(immediatelyInteractingElements);
        // }
        // }
        // }
        // }
    }

    // TODO FIX THIS SHIT
    private List<Entry<Evaluatable, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
        // return this.getConditionActionPairs().entrySet().stream()
        // .filter(o -> o.getKey()..equals(conditionActionPairIdentifier))
        // .collect(Collectors.toList());
        return null;
    }

}
