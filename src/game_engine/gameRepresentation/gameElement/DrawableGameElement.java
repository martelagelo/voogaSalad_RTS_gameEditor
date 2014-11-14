package game_engine.gameRepresentation.gameElement;

import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DrawableGameElement extends GameElement implements Boundable {

    protected Group visualRepresentation = new Group();
    protected Map<String, List<DrawableGameElement>> interactingElements;

    public DrawableGameElement (Image image, Point2D position, String name) {
        this.textualAttributes.add(new Attribute<String>("Name", name));
        this.visualRepresentation.setTranslateX(position.getX());
        this.visualRepresentation.setTranslateY(position.getY());
        this.visualRepresentation.getChildren().add(new ImageView(image));
    }

    public void addCollidingElements (List<DrawableGameElement> collidingElements) {
        interactingElements.put("CollidingElements", collidingElements);
    }

    public void addVisibleElements (List<DrawableGameElement> visibleElements) {
        interactingElements.put("VisibleElements", visibleElements);
    }

    public void update () {
        updateSelfDueToCollisions();
        updateSelfDueToVisions();
        updateSelfDueToInternalFactors();
        updateSelfDueToCurrentObjective();
    }

    private void updateSelfDueToCurrentObjective () {
        // TODO Auto-generated method stub

    }

    private void updateSelfDueToInternalFactors () {
        // TODO Auto-generated method stub

    }

    private void updateSelfDueToVisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("VisionCondition",
                                                                "VisibleElements");
    }

    private void updateSelfDueToCollisions () {
        evaluateConditionActionPairsOnInteractingElementsSubset("CollisionCondition",
                                                                "CollidingElements");
    }

    private List<Entry<Condition, Action>> getApplicableConditionActionPairs (String conditionActionPairIdentifier) {
        return this.ifThisThenThat.entrySet().stream()
                .filter(o -> o.getKey().getType().equals(conditionActionPairIdentifier))
                .collect(Collectors.toList());
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

    @Override
    public Bounds getBounds () {
        return this.visualRepresentation.getChildren().get(0).getBoundsInParent();
    }

    public Group getVisibleRepresentation () {
        // TODO added this getter for testing purposes, really not sure how to actually add a
        // drawable game element to the scene?
        return this.visualRepresentation;
    }

}
