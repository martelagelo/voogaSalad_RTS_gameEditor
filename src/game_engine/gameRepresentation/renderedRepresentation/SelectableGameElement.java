package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;


/**
 * 
 * @author Jonathan Tseng, Steve, Nishad, Rahul
 *
 */
public class SelectableGameElement extends DrawableGameElement {

    private SelectableGameElementState myState;
    private boolean selected;

    public SelectableGameElement (DrawableGameElementState element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    public String getType () {
        return myState.getType();
    }

    public void select (boolean select) {
        selected = select;
        myAnimation.select(select);
    }

    @Override
    public void update () {
        super.update();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        // TODO Auto-generated method stub
        List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
                getApplicableConditionActionPairs("ObjectiveCondition");
    }

    public void updateSelfDueToSelection () {
        // TODO Auto-generated method stub
        // update representation?
        // visualRepresentation.setSelected();
        List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
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
//        List<Entry<Evaluatable, Action>> applicableConditionActionPairs =
//                getApplicableConditionActionPairs(conditionActionPairIdentifier);
//        if (myState.getInteractingElements().containsKey(elementIdentifier)) {
//            for (DrawableGameElementState element : myState.getInteractingElements()
//                    .get(elementIdentifier)) {
//                List<GameElementState> immediatelyInteractingElements =
//                        new ArrayList<GameElementState>();
//                for (Entry<Evaluatable, Action> conditionActionPair : applicableConditionActionPairs) {
//                    if (conditionActionPair.getKey().evaluate(this, element)) {
//                        conditionActionPair.getValue().doAction(immediatelyInteractingElements);
//                    }
//                }
//            }
//        }
    }

    // TODO FIX THIS SHIT
    private List<Entry<Evaluatable, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
//        return this.myConditionActionPairs.entrySet().stream()
//                .filter(o -> o.getKey().getType().equals(conditionActionPairIdentifier))
//                .collect(Collectors.toList());
        return null;

    }

}
