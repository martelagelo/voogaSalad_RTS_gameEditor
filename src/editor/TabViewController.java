package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
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
    private Accordion levelElementAccordian;
    @FXML
    private ElementAccordianController levelElementAccordianController;
    @FXML
    private ScrollPane levelTriggersView;
    @FXML
    private BorderPane gameRunnerPane;
    @FXML
    private BorderPane tabPane;

    // TODO: Clean up this function
    private void initAccordianPanes () {
        // TODO Get Accordian Pane MetaData
        // call to levelElementAccordianController to set up the data
    }
    
    @Override
    public void update () {
        // Get Accordian Pane MetaData
        // call to levelElementAccordianController to update the data
    }

    @Override
    public void initialize () {
        attachChildContainers(levelElementAccordianController);
        initAccordianPanes();
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }
    
}
