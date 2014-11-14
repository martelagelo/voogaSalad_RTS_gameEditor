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


/**
 * This GameElement is drawable but not necessarily selectable - examples include terrain. These
 * elements must have a bounding box.
 * 
 * @author Steve
 *
 */

public class DrawableGameElement extends GameElement implements Boundable {

    protected Group visualRepresentation = new Group();
    protected Map<String, List<DrawableGameElement>> interactingElements;

    public DrawableGameElement (Image image, Point2D position, String name) {
        this.textualAttributes.add(new Attribute<String>("Name", name));
        this.visualRepresentation.setTranslateX(position.getX());
        this.visualRepresentation.setTranslateY(position.getY());
        this.visualRepresentation.getChildren().add(new ImageView(image));
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
