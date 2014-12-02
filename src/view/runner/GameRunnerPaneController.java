package view.runner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import view.gui.GUIContainer;


/**
 * Game Runner View Controller
 * holds scene graph as well as button pane, status pane, and minimap
 * 
 * @author Jonathan Tseng
 *
 */
public class GameRunnerPaneController extends GUIContainer {

    @FXML
    private BorderPane root;
    @FXML
    private BorderPane sceneGraphPane;
    @FXML
    private GridPane buttonPane;
    @FXML
    private Pane statusPane;
    @FXML
    private BorderPane minimapPane;
    
    @Override
    public Node getRoot () {
        return root;
    }

    public void attachSceneGraph (Node sceneGraph) {
        sceneGraphPane.setCenter(sceneGraph);
    }

    public void attachMiniMap (Node miniMap) {
        minimapPane.setCenter(miniMap);
    }

    @Override
    protected void init () {

    }

    @Override
    public void update () {

    }

}