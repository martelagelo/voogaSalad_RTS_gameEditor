package game_engine.gameRepresentation;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;


public class SelectableGameElement extends DrawableGameElement {

    protected GridPane abilityRepresentation;
    protected Node informationRepresentation;
    
    public SelectableGameElement (Image image, Point2D position, String name) {
        super(image, position, name);
    }

}
