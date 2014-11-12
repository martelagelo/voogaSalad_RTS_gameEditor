package editor;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
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

    @FXML
    private TabPane rootViewTabPane;
    @FXML
    private TreeView<String> rootViewProjectExplorer;
    @FXML
    private VBox rootViewGameInfoVBox;
    @FXML
    private Parent editorMenuBar;
    @FXML
    private EditorMenuBar editorMenuBarController;
    @FXML
    private BorderPane editorRoot;

    @Override
    @FXML
    public void initialize () {
        System.out.println("initializing editor screen...");
        initTabs();
        initGameHierarchy();
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

    private void initGameHierarchy () {
        TreeItem<String> item1 = new TreeItem<>("tree1");
        item1.getChildren().add(new TreeItem<String>("child1"));
        item1.getChildren().add(new TreeItem<String>("child2"));
        rootViewProjectExplorer.setRoot(item1);
    }

    private void initGameInfoVBox () {
        Label lab = new Label("my game");
        rootViewGameInfoVBox.getChildren().add(lab);
    }

    private void initTabs () {
        rootViewTabPane
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
        rootViewTabPane.getTabs().add(tab);
    }

    private void tabChanged (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        System.out.println("tab changed");
    }

}
