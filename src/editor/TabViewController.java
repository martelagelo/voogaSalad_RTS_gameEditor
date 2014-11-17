package editor;

import view.GUIController;
import view.GUILoadStyleUtility;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class TabViewController implements GUIController {

    @FXML private Accordion levelElementAccordian;
    @FXML private VBox levelInfoView;
    @FXML private ScrollPane levelTriggersView;
    @FXML private ScrollPane levelMapView;
    @FXML private BorderPane levelMiniMapView;
    @FXML private ScrollPane levelElementAttributesView;
    @FXML private ScrollPane levelElementTriggersView;
    @FXML private BorderPane tabPane;

    @Override @FXML
    public void initialize() {
        initAccordianPanes();
    }

    //TODO: Clean up this function
    private void initAccordianPanes() {
        //TODO Get Accordian Pane MetaData
        String filePath = "/editor/guipanes/GameElementDropDown.fxml";
        GUILoadStyleUtility util = new GUILoadStyleUtility();
        ElementDropDownControl dropDownController = (ElementDropDownControl) util.generateGUIPane(filePath);
        dropDownController.setGameElement("Unit");
        dropDownController.addElement("item1", null);
        dropDownController.addElement("item2", new Rectangle(50,50));

        levelElementAccordian.getPanes().add((TitledPane)dropDownController.getRoot());  
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }

    @Override
    public String[] getCSS () {
        return new String[] { "/editor/stylesheets/editorTab.css" };
    }

}
