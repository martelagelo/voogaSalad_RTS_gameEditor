package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import view.GUIContainer;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class TabViewController extends GUIContainer {

    @FXML
    private ScrollPane levelTriggersView;
    @FXML
    private BorderPane gameRunnerPane;
    @FXML
    private BorderPane tabPane;

    @Override
    public void update () {

    }

    @Override
    public void init () {

    }

    @Override
    public Node getRoot () {
        return tabPane;
    }

}
