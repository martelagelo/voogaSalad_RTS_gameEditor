package game_engine.gameRepresentation;

import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;


public class DrawableGameElement extends GameElement {

    private Node visualRepresentation;
    private Map<String, List<GameElement>> interactingElements;

}
