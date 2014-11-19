package editor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import view.GUIContainer;
import view.GUILoadStyleUtility;


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
        String filePath = "/editor/guipanes/GameElementDropDown.fxml";
        GUILoadStyleUtility util = new GUILoadStyleUtility();
        ElementDropDownControl dropDownController = (ElementDropDownControl) util
                .generateGUIPane(filePath);
        dropDownController.setGameElement("Unit");
        dropDownController.addElement("item1", null);
        dropDownController.addElement("item2", new Rectangle(50, 50));

        levelElementAccordian.getPanes().add((TitledPane) dropDownController.getRoot());
    }
    
    @Override
    public void update () {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize () {
        initAccordianPanes();
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }
    
}
