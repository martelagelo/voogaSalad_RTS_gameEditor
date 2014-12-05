package view.runner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
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