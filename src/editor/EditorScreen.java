package editor;

import java.util.HashMap;
import java.util.Observable;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIController;
import view.GUILoadStyleUtility;


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
    @FXML private VBox gameInfoBox;
    @FXML private InformationBox gameInfoBoxController;
    @FXML private Parent editorMenuBar;
    @FXML private EditorMenuBar editorMenuBarController;
    @FXML private BorderPane editorRoot;

    private HashMap<String, TabViewController> myTabViewControllers;
    private Tab myCurrentTab;
    
    @Override
    @FXML
    public void initialize () {
        myTabViewControllers = new HashMap<>();
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
        projectExplorerController.addCampaigns("campaign1", "campaign2");
        projectExplorerController.addLevels("campaign1", "howdy", "howdy2");
        projectExplorerController.setOnSelectionChanged((String s)->{System.out.println("cool" + s);});
        projectExplorerController.setOnLevelClicked((String s)->{launchTab(s);});
    }

    private void initGameInfoVBox () {
        String val = projectExplorerController.selectedProjectItemProperty().get();
        Image icon = new Image("file:\\C:\\Users\\Jonathan Tseng\\Desktop\\Duke Schedule.jpg");
        gameInfoBoxController.setInfo(val, val, val, icon);
    }

    private void initTabs () {
        tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener( (observable, oldTab, newTab) -> tabChanged(observable, oldTab, newTab));
    }

    private void launchTab (String level) {
        Optional<Tab> option = tabPane.getTabs().stream().filter((tab)->tab.getId().equals(level)).findFirst();
        if (option.isPresent()) {
            myCurrentTab = option.get();
        } else {
            createNewTab(level);
        }
        tabPane.getSelectionModel().select(myCurrentTab);
    }

    private void createNewTab(String level) {
        Tab tab = new Tab();
        tab.setText(level);
        tab.setId(level);
        
        String filePath = "/editor/guipanes/EditorTabView.fxml";
        GUILoadStyleUtility util = new GUILoadStyleUtility();
        TabViewController tabController = (TabViewController) util.generateGUIPane(filePath);
        
        myCurrentTab = tab;
        tab.setContent((BorderPane) tabController.getRoot());
        tabPane.getTabs().add(tab);
        myTabViewControllers.put(level, tabController);
    }
    
    private void tabChanged (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        System.out.println("tab changed");
        myCurrentTab = newTab;
    }

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }

}
