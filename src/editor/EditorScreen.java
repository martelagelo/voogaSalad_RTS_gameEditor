package editor;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUILoadStyleUtility;
import view.GUIScreen;
import view.WizardUtility;
import editor.wizards.Wizard;
import editor.wizards.WizardData;
import game_engine.gameRepresentation.stateRepresentation.DescribableState;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import gamemodel.exceptions.CampaignNotFoundException;
import gamemodel.exceptions.LevelNotFoundException;


/**
 * 
 * Screen for the Editor. Holds many sub-GUI panes
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */

public class EditorScreen extends GUIScreen {

    private static final String TERRAIN_WIZARD = "/editor/wizards/guipanes/TerrainWizard.fxml";
    private static final String GAME_ELEMENT_WIZARD = "/editor/wizards/guipanes/DrawableGameElementWizard.fxml";
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
    @FXML
    private Button save;
    @FXML
    private Accordion levelElementAccordian;
    @FXML
    private ElementAccordianController levelElementAccordianController;

    private HashMap<String, TabViewController> myTabViewControllers;
    private Tab myCurrentTab;

    private void openGameElementWizard () {
        Wizard wiz = WizardUtility.loadWizard(GAME_ELEMENT_WIZARD, new Dimension(800, 600));
        Consumer<WizardData> c = (data) -> {
//            System.out.println(data);
            myMainModel.createDrawableGameElement(data);
            wiz.getStage().close();
        };
        wiz.setSubmit(c);
    }

    @Override
    public Node getRoot () {
        return editorRoot;
    }

    // TODO: Clean up this function
    private void initAccordion () {
        // TODO Get Accordian Pane MetaData
        // call to levelElementAccordianController to set up the data
    }

    private void initProjectExplorer () {
        // TODO: on selection changed should update the info box
        projectExplorerController.setOnSelectionChanged( (String[] selection) -> {
            System.out.println(Arrays.toString(selection));
            updateInfoBox(selection);
        });
        projectExplorerController.setOnLevelClicked( (String s) -> {
            launchTab(s);
        });
    }

    private void initInfoBox () {
        gameInfoBoxController.setSubmitAction( (name, description) -> {
            try {
                myMainModel.updateDescribableState(projectExplorerController.getSelectedHierarchy(), name,
                                                   description);
            }
            catch (CampaignNotFoundException | LevelNotFoundException e) {
                System.out.println("Failed to update selected describable state");
                e.printStackTrace();
            }    
        });
    }

    private void updateInfoBox (String[] selection) {
        try {
            DescribableState state = myMainModel.getDescribableState(selection);
            gameInfoBoxController.setText(state.getName(), state.getDescription());
            System.out.println("updating: " + state.getName() + ", " + state.getDescription());
        }
        catch (Exception e) {
            System.out.println("shit update info box failed");
            e.printStackTrace();
        }
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
    public void init () {
        attachChildContainers(editorMenuBarController, levelElementAccordianController);
        myTabViewControllers = new HashMap<>();
        initTabs();
        initProjectExplorer();
        initAccordion();
        initInfoBox();
//        newGameElement.setOnAction(e -> openGameElementWizard());
//        save.setOnAction(e -> myMainModel.saveGame());
    }

    @Override
    public void update () {
        updateAccordion();
        updateProjectExplorer();
        updateTabViewControllers();
        updateInfoBox(projectExplorerController.getSelectedHierarchy());
    }

    // TODO: metadata stuff
    private void updateAccordion () {

    }

    private void updateProjectExplorer () {
        GameState game = myMainModel.getCurrentGame();
        Map<String, List<String>> campaignLevelMap = new HashMap<>();
        List<String> campaigns = game.getCampaigns().stream().map( (campaign) -> {
            return campaign.getName();
        }).collect(Collectors.toList());
        game.getCampaigns().forEach( (campaignState) -> {
            campaignLevelMap.put(campaignState.getName(), campaignState
                    .getLevels().stream().map( (level) -> {
                        return level.getName();
                    }).collect(Collectors.toList()));
        });
        projectExplorerController.update(game.getName(), campaigns, campaignLevelMap);
    }

    private void updateTabViewControllers () {

    }

}
