package game_engine.gameRepresentation;

import game_engine.computers.boundsComputer.Boundable;
import game_engine.gameRepresentation.actions.Action;
import game_engine.gameRepresentation.conditions.Condition;
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

    protected Group visualRepresentation;
    protected Map<String, List<DrawableGameElement>> interactingElements;

    public DrawableGameElement (Image image, Point2D position, String name) {
        this.textualAttributes.put("Name", name);
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
    }

    private void updateSelfDueToVisions () {
        // TODO Auto-generated method stub

    }

    private void updateSelfDueToCollisions () {
        List<Entry<Condition, Action>> applicableConditionActionPairs =
                this.ifThisThenThat.entrySet().stream()
                        .filter(o -> o.getKey().getType().equals("CollisionCondition"))
                        .collect(Collectors.toList());

        for (DrawableGameElement collidingElement : interactingElements.get("CollidingElements")) {
            for (Entry<Condition, Action> conditionActionPair : applicableConditionActionPairs) {
                if (conditionActionPair.getKey().evaluate()) {
                    conditionActionPair.getValue().doAction();
                }
            }
        }

    }

    @Override
    public Bounds getBounds () {
        return this.visualRepresentation.getChildren().get(0).getBoundsInParent();
    }

}
