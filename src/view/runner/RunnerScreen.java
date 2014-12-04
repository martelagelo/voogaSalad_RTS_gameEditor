package view.runner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
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
    @FXML
    MenuBar runnerMenuBar;
    @FXML
    RunnerMenuBarController runnerMenuBarController;
    
    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    protected void init () {
        attachChildContainers(runnerMenuBarController);
        runnerMenuBarController.attachScreen(this);
    }

    @Override
    public void update () {

    }

}
