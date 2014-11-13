package game_engine.gameRepresentation;

import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DrawableGameElement extends GameElement {

    protected Group visualRepresentation;
    protected Map<String, List<GameElement>> interactingElements;
    
    public DrawableGameElement(Image image, Point2D position, String name){
        this.textualAttributes.put("Name", name);
        this.visualRepresentation.setTranslateX(position.getX());
        this.visualRepresentation.setTranslateY(position.getY());
        this.visualRepresentation.getChildren().add(new ImageView(image));
    }

}
