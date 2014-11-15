package game_engine.gameRepresentation.gameElement;

import game_engine.animationEngine.AnimationEngine;
import game_engine.computers.boundsComputer.Boundable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;


/**
 * This GameElement is drawable but not necessarily selectable - examples include terrain. These
 * elements must have a bounding box.
 * 
 * @author Steve
 *
 */

public class DrawableGameElement extends GameElement implements Boundable {

    protected AnimationEngine visualRepresentation;

    public DrawableGameElement (Image image, Point2D position, String name) {
        this.textualAttributes.add(new Attribute<String>("Name", name));
        // this.visualRepresentation.setTranslateX(position.getX());
        // this.visualRepresentation.setTranslateY(position.getY());
        // this.visualRepresentation.getChildren().add(new ImageView(image));
    }

    @Override
    public Bounds getBounds () {
        return visualRepresentation.getBounds();
    }

    public Node getVisibleRepresentation () {
        // TODO added this getter for testing purposes, really not sure how to actually add a
        // drawable game element to the scene?
        return visualRepresentation.getNode();
    }

}
