package vooga.engine.gameRepresentation;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class DrawableGameElement extends GameElement {
    
    private Node visualRepresentation;
    private GridPane abilityRepresentation;
    private Node informationRepresentation;
    private List<GameElement> collidingElements;
    private List<GameElement> visibileElements;

}
