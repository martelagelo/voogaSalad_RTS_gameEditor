package editor;

import editor.wizards.Wizard;
import editor.wizards.WizardData;
import game_engine.visuals.Dimension;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.GUILoadStyleUtility;
import view.GUIScreen;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */

public class EditorScreen extends GUIScreen {

    @FXML
    private TabPane tabPane;
    @FXML
    private TreeView<String> projectExplorer;
    @FXML
    private ProjectExplorerController projectExplorerController;
    @FXML
    private VBox gameInfoBox;
    @FXML
    private DescribableInfoBoxController gameInfoBoxController;
    @FXML
    private Parent editorMenuBar;
    @FXML
    private EditorMenuBarController editorMenuBarController;
    @FXML
    private BorderPane editorRoot;
    @FXML
    private Button newGameElement;
    @FXML
    private Button newTerrain;

    private HashMap<String, TabViewController> myTabViewControllers;
    private Tab myCurrentTab;

    /**
     * TODO: Jonathan is cleaning up the load utility
     */
    private void openGameElementWizard () {
        Dimension dim = new Dimension(600, 300);
        loadWizard("/editor/wizards/guipanes/GameElementWizard.fxml", dim);
    }

    /**
     * TODO: Jonathan is cleaning up the load utility
     */
    private void openTerrainWizard () {
        Dimension dim = new Dimension(600, 300);
        loadWizard("/editor/wizards/guipanes/TerrainWizard.fxml", dim);
    }

    private void loadWizard (String filePath, Dimension dim) {
        Wizard wiz = (Wizard) GUILoadStyleUtility.generateGUIPane(filePath);
        Stage s = new Stage();
        Scene myScene = new Scene((Parent) wiz.getRoot(), dim.getWidth(), dim.getHeight());
        s.setScene(myScene);
        s.show();
        Consumer<WizardData> c = (data) -> {
            System.out.println(data);
            s.close();
        };
        wiz.setSubmit(c);
    }

    @Override
    public Node getRoot () {
        return editorRoot;
    }

    private void initProjectExplorer () {
        projectExplorerController.bindGameName(new SimpleStringProperty("Game test"));
        projectExplorerController.addCampaigns("campaign1", "campaign2");
        projectExplorerController.addLevels("campaign1", "howdy", "howdy2");
        projectExplorerController.setOnSelectionChanged( (String s) -> {
            System.out.println("cool" + s);
        });
        projectExplorerController.setOnLevelClicked( (String s) -> {
            launchTab(s);
        });
    }

    private void initGameInfoVBox () {
        String val = projectExplorerController.selectedProjectItemProperty().get();
        gameInfoBoxController.setInfo(val, val, val, null);
    }

    private void initTabs () {
        tabPane
                .getSelectionModel()
                .selectedItemProperty()
                .addListener( (observable, oldTab, newTab) -> tabChanged(observable, oldTab, newTab));
    }

    private void launchTab (String level) {
        Optional<Tab> option =
                tabPane.getTabs().stream().filter( (tab) -> tab.getId().equals(level)).findFirst();
        if (option.isPresent()) {
            myCurrentTab = option.get();
        }
        else {
            createNewTab(level);
        }
        tabPane.getSelectionModel().select(myCurrentTab);
    }

    private void createNewTab (String level) {
        Tab tab = new Tab();
        tab.setText(level);
        tab.setId(level);

        String filePath = "/editor/guipanes/EditorTabView.fxml";
        TabViewController tabController =
                (TabViewController) GUILoadStyleUtility.generateGUIPane(filePath);
        clearChildContainers();
        attachChildContainers(tabController);

        myCurrentTab = tab;
        tab.setContent((BorderPane) tabController.getRoot());
        tabPane.getTabs().add(tab);
        myTabViewControllers.put(level, tabController);
    }

    private void tabChanged (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        myCurrentTab = newTab;
    }

    @Override
    public void initialize () {
        myTabViewControllers = new HashMap<>();
        initTabs();
        initProjectExplorer();
        initGameInfoVBox();
        newGameElement.setOnAction(e -> openGameElementWizard());
        newTerrain.setOnAction(e -> openTerrainWizard());
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub

    }

}
