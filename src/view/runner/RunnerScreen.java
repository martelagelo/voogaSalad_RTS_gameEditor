package view.runner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import view.gui.GUIScreen;

/**
 * Game Runner Screen, holds a Game Runner View Node
 * @author Jonathan Tseng
 *
 */
public class RunnerScreen extends GUIScreen {

    @FXML
    BorderPane root;
    @FXML
    BorderPane gameRunner;
    @FXML 
    GameRunnerPaneController runnerController;
    

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
