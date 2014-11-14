package game_engine.gameRepresentation.gameElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import game_engine.computers.boundsComputers.Sighted;
import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


/**
 * This is the most widely used GameElement. This type of GameElement has both a bounding box and a
 * vision box. This is the element that reacts to collisions with DrawableGameElements.
 * 
 * @author Steve
 *
 */
public class SelectableGameElement extends DrawableGameElement implements
        Sighted {

    protected Map<String, Map<String, ObscureAction>> allAbilityRepresentations;
    private Map<String, ObscureAction> currentAbilityRepresentation;
    protected Node informationRepresentation;

    public SelectableGameElement (Image image, Point2D position, String name) {
        super(image, position, name);
    }

    @Override
    public Polygon getVisionPolygon () {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                                   new Double[] {
                                                 this.getBounds().getMinX(),
                                                 this.getBounds().getMinY(),
                                                 this.getBounds().getMinX()
                                                         + this.getBounds().getWidth(),
                                                 this.getBounds().getMinY(),
                                                 this.getBounds().getMaxX(),
                                                 this.getBounds().getMaxY(),
                                                 this.getBounds().getMinX(),
                                                 this.getBounds().getMinY()
                                                         + this.getBounds().getHeight(), });
        return polygon;
    }

    private void updateAbilityRepresentation (String identifier) {
        currentAbilityRepresentation = allAbilityRepresentations.get(identifier);
    }

    public Map<String, ObscureAction> getCurrentInteractionInformation () {
        return currentAbilityRepresentation;
    }

    public void addCollidingElements (List<DrawableGameElement> collidingElements) {
        interactingElements.put("CollidingElements", collidingElements);
    }

    public void addVisibleElements (List<DrawableGameElement> visibleElements) {
        interactingElements.put("VisibleElements", visibleElements);
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

        for (DrawableGameElement element : interactingElements.get(elementIdentifier)) {
            List<GameElement> immediatelyInteractingElements = new ArrayList<GameElement>();
            immediatelyInteractingElements.add(this);
            immediatelyInteractingElements.add(element);
            for (Entry<Condition, Action> conditionActionPair : applicableConditionActionPairs) {
                if (conditionActionPair.getKey().evaluate(immediatelyInteractingElements)) {
                    conditionActionPair.getValue().doAction(immediatelyInteractingElements);
                }
            }
        }
    }
    
    private List<Entry<Condition, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
        return this.ifThisThenThat.entrySet().stream()
                .filter(o -> o.getKey().getType().equals(conditionActionPairIdentifier))
                .collect(Collectors.toList());
    }

}
