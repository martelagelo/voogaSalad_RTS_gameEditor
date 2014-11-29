package view.gamerunner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import view.GUIContainer;


/**
 * Game Runner View Controller
 * holds scene graph as well as button pane, status pane, and minimap
 * 
 * @author Jonathan Tseng
 *
 */
public class GameRunnerViewController extends GUIContainer {

    @FXML
    private BorderPane root;
    @FXML
    private Pane sceneGraphPane;
    @FXML
    private GridPane buttonPane;
    @FXML
    private Pane statusPane;
    @FXML
    private Pane minimapPane;

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    protected void init () {
        
    }

    @Override
    public void update () {

    }

}
