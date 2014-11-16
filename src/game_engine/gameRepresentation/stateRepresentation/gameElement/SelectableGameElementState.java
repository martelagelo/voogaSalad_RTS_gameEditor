package game_engine.gameRepresentation.stateRepresentation.gameElement;

import game_engine.computers.boundsComputers.Sighted;
import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import game_engine.gameRepresentation.conditions.conditionsOnImmediateAttributes.ConditionOnImmediateElements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.scene.shape.Polygon;


/**
 * This is the most widely used GameElement. This type of GameElement has both a bounding box and a
 * vision box. This is the element that reacts to collisions with DrawableGameElements.
 * 
 * @author Steve
 *
 */
public class SelectableGameElementState extends DrawableGameElementState implements
        Sighted {

    protected Map<String, Map<String, ObscureAction>> allAbilityRepresentations;
    private Map<String, ObscureAction> currentAbilityRepresentation;
    protected Map<String, ArrayList<DrawableGameElementState>> interactingElements;
    

    public SelectableGameElementState (Number xPosition, Number yPosition) {
        super(xPosition, yPosition);
        interactingElements = new HashMap<String, ArrayList<DrawableGameElementState>>();
        // TODO Auto-generated constructor stub
    }

    public void update () {
        super.update();
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        // TODO Auto-generated method stub
        List<Entry<Condition, Action>> applicableConditionActionPairs =
                getApplicableConditionActionPairs("ObjectiveCondition");

    }

    public void updateSelfDueToSelection () {
        // TODO Auto-generated method stub
        // update representation?
        // visualRepresentation.setSelected();
        List<Entry<Condition, Action>> applicableConditionActionPairs =
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

    private void evaluateConditionActionPairsOnInteractingElementsSubset (String conditionActionPairIdentifier,
                                                                          String elementIdentifier) {
        List<Entry<Condition, Action>> applicableConditionActionPairs =
                getApplicableConditionActionPairs(conditionActionPairIdentifier);
        if (interactingElements.containsKey(elementIdentifier)) {
            for (DrawableGameElementState element : interactingElements.get(elementIdentifier)) {
                List<GameElementState> immediatelyInteractingElements =
                        new ArrayList<GameElementState>();
                for (Entry<Condition, Action> conditionActionPair : applicableConditionActionPairs) {
                    if (conditionActionPair.getKey().evaluate(this, element)) {
                        conditionActionPair.getValue().doAction(immediatelyInteractingElements);
                    }
                }
            }
        }
    }

    private List<Entry<Condition, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
        return this.ifThisThenThat.entrySet().stream()
                .filter(o -> o.getKey().getType().equals(conditionActionPairIdentifier))
                .collect(Collectors.toList());
    }

    public void addCollidingElements (List<DrawableGameElementState> collidingElements) {
        for (DrawableGameElementState element : collidingElements) {
            addInteractingElement("CollidingElements", element);
        }
    }

    public void addVisibleElements (List<DrawableGameElementState> visibleElements) {
        for (DrawableGameElementState element : visibleElements) {
            addInteractingElement("VisibleElements", element);
        }
    }

    public void addInteractingElement (String elementType, DrawableGameElementState element) {
        ArrayList<DrawableGameElementState> elements = new ArrayList<DrawableGameElementState>();
        elements.addAll(interactingElements.get(elementType));
        elements.add(element);
        interactingElements.put(elementType, elements);
    }

    @Override
    public Polygon getVisionPolygon () {
        // TODO do this
        return null;
        // return visualRepresentation.getVisionPolygon();
    }

    private void updateAbilityRepresentation (String identifier) {
        currentAbilityRepresentation = allAbilityRepresentations.get(identifier);
    }

    public Map<String, ObscureAction> getCurrentInteractionInformation () {
        return currentAbilityRepresentation;
    }

}
