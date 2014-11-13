package editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIController;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class EditorScreen implements GUIController {

    @FXML private TabPane tabPane;
    @FXML private TreeView<String> projectExplorer;
    @FXML private ProjectExplorerController projectExplorerController;
    @FXML private VBox gameInfoVBox;
    @FXML private Parent editorMenuBar;
    @FXML private EditorMenuBar editorMenuBarController;
    @FXML private BorderPane editorRoot;

    @Override
    @FXML
    public void initialize () {
        System.out.println("initializing editor screen...");
        initTabs();
        initProjectExplorer();
        initGameInfoVBox();
    }

    @Override
    public Node getRoot () {
        return editorRoot;
    }

    @Override
    public String[] getCSS () {
        return new String[] { "/editor/stylesheets/editorRoot.css" };
    }

    private void initProjectExplorer () {
        projectExplorerController.bindGameName(new SimpleStringProperty("Game test"));
        projectExplorerController.addCampaign("campaign1");
        projectExplorerController.addCampaign("campaign2");
        projectExplorerController.addLevel("campaign1", "howdy");
        projectExplorerController.addLevel("campaign1", "howdy2");
        projectExplorerController.setOnGameClicked((String s)->{System.out.println(s);});
        projectExplorerController.setOnCampaignClicked((String s)->{System.out.println(s);});
        projectExplorerController.setOnLevelClicked((String s)->{System.out.println(s);});
    }

    private void initGameInfoVBox () {
        Label lab = new Label("my game");
        gameInfoVBox.getChildren().add(lab);
    }

    private void initTabs () {
        tabPane
                .getSelectionModel()
                .selectedItemProperty()
                .addListener( (observable, oldTab, newTab) -> tabChanged(observable, oldTab, newTab));

        addTab();
        addTab();
    }

    private void addTab () {
        Tab tab = new Tab();
        tab.setId("something");
        tab.setText("who cares");

        String filePath = "/editor/guipanes/EditorTabView.fxml";
        GUILoadStyleUtility util = new GUILoadStyleUtility();
        GUIController tabController = util.generateGUIPane(filePath);

        tab.setContent((BorderPane) tabController.getRoot());
        tabPane.getTabs().add(tab);
    }

    private void tabChanged (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        System.out.println("tab changed");
    }

}
