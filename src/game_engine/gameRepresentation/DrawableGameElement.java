package game_engine.gameRepresentation;

import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DrawableGameElement extends GameElement implements Boundable {

    protected Group visualRepresentation;
    protected Map<String, List<GameElement>> interactingElements;

    public DrawableGameElement (Image image, Point2D position, String name) {
        this.textualAttributes.put("Name", name);
        this.visualRepresentation.setTranslateX(position.getX());
        this.visualRepresentation.setTranslateY(position.getY());
        this.visualRepresentation.getChildren().add(new ImageView(image));
    }

    public void addCollidingElements (List<GameElement> collidingElements) {
        interactingElements.put("CollidingElements", collidingElements);
    }

    public void addVisibleElements (List<GameElement> visibleElements) {
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
        // TODO Auto-generated method stub

    }

}
