package game_engine.gameRepresentation;

import game_engine.computers.boundsComputer.Boundable;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DrawableGameElement extends GameElement implements Boundable {

    protected Group visualRepresentation = new Group();
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

    @Override
    public Bounds getBounds () {
        return this.visualRepresentation.getChildren().get(0).getBoundsInParent();
    }
    
    public Group getVisibleRepresentation(){
        // TODO added this getter for testing purposes, really not sure how to actually add a drawable game element to the scene?
        return this.visualRepresentation;
    }

}
