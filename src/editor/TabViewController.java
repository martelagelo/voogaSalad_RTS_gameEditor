package editor;

import view.GUIController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    @FXML
    private Accordion levelElementAccordian;
    @FXML
    private VBox levelInfoView;
    @FXML
    private ScrollPane levelTriggersView;
    @FXML
    private ScrollPane levelMapView;
    @FXML
    private BorderPane levelMiniMapView;
    @FXML
    private ScrollPane levelElementAttributesView;
    @FXML
    private ScrollPane levelElementTriggersView;
    
    @FXML
    private BorderPane tabPane;
  
    @Override
    @FXML
    public void initialize() {
        //initAccordianPanes();
        initLevelInfoInputs();
    }

    private void initAccordianPanes() {
        //TODO Get Accordian Pane MetaData
        /*
        GameElementAccordianPane pane = new GameElementAccordianPane("temp1");
        Rectangle r = new Rectangle(100, 20);
        r.setFill(Color.BLUE);
        Rectangle r2 = new Rectangle(100, 20);
        r2.setFill(Color.RED);
        Rectangle r3 = new Rectangle(100, 20);
        r3.setFill(Color.GREEN);
        pane.addElement("item1", r);
        pane.addElement("item2", r2);
        pane.addElement("item3", r3);
        
        
        GameElementAccordianPane pane2 = new GameElementAccordianPane("temp2");
        Rectangle r4 = new Rectangle(100, 20);
        r4.setFill(Color.BLUE);
        Rectangle r5 = new Rectangle(100, 20);
        r5.setFill(Color.RED);
        Rectangle r6 = new Rectangle(100, 20);
        r6.setFill(Color.GREEN);
        pane2.addElement("item1", r4);
        pane2.addElement("item2", r5);
        pane2.addElement("item3", r6);
        */
        ElementDropDownControl pane = new ElementDropDownControl();
        
//        levelElementAccordian.getPanes().addAll(pane);  
    }

    private void initLevelInfoInputs() {
        Label levelNameLabel = new Label("Level Name: ");
        TextField levelNameTextField = new TextField();
        Label levelInfoLabel = new Label("Level Description: ");
        TextArea levelInfoTextArea = new TextArea();
        levelInfoView.getChildren().addAll(levelNameLabel,
                                           levelNameTextField,
                                           levelInfoLabel,
                                           levelInfoTextArea);

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
