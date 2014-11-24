package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIContainer;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class TabViewController extends GUIContainer {

    @FXML
    private VBox levelTriggersView;
    @FXML
    private BorderPane gameRunnerPane;
    @FXML
    private BorderPane tabPane;
    @FXML
    private Button newLevelTrigger;
    @FXML
    private ListView levelTriggers;
    
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
