package view.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelIdentifier;
import view.GUILoadStyleUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIPanePath;
import view.gui.GUIScreen;


/**
 * 
 * Screen for the Editor. Holds many sub-GUI panes
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
    @FXML
    private Button save;
    @FXML
    private Accordion levelElementAccordion;
    @FXML
    private ElementAccordionController levelElementAccordionController;

    private List<TabViewController> myTabViewControllers;

    @Override
    public Node getRoot () {
        return editorRoot;
    }

    private void initProjectExplorer () {
        projectExplorerController.setOnSelectionChanged( (String[] selection) -> {
            updateInfoBox(selection);
        });
        projectExplorerController.setOnLevelClicked( (String level) -> {
            String campaign = projectExplorerController.getSelectedHierarchy()[1];
            launchTab(new LevelIdentifier(level, campaign));
        });
    }

    public void updateModelToSave () {
        myTabViewControllers.forEach( (tabController) -> {
            tabController.updateModelToSave();
        });
    }

    private void initInfoBox () {
        gameInfoBoxController
                .setSubmitAction( (name, description) -> {
                    try {
                        myMainModel.updateDescribableState(projectExplorerController
                                .getSelectedHierarchy(), name,
                                                           description);
                    }
                    catch (CampaignNotFoundException | LevelNotFoundException e) {
                        DialogBoxUtility.createMessageDialog(String
                                .format(
                                        "Failed to update selected describable state due to: %s",
                                        e.toString()));
                    }
                });
    }

    private void updateInfoBox (String[] selection) {
        try {
            DescribableState state = myMainModel.getDescribableState(selection);
            gameInfoBoxController.setText(state.getName(), state.getDescription());
        }
        catch (Exception e) {
            DialogBoxUtility.createMessageDialog(String
                    .format(
                            "Failed to update InfoBox based on project selection due to: %s",
                            e.toString()));
        }
    }

    private void launchTab (LevelIdentifier levelID) {
        Optional<Tab> option = tabPane
                .getTabs()
                .stream()
                .filter( (tab) -> ((LevelIdentifier) tab.getUserData())
                        .equals(levelID)).findFirst();
        Tab tab = (option.isPresent()) ? option.get() : createNewTab(levelID);
        tabPane.getSelectionModel().select(tab);
    }

    private Tab createNewTab (LevelIdentifier levelID) {
        Optional<TabViewController> option = myTabViewControllers.stream()
                .filter( (tabController) -> tabController.isLevel(levelID)).findFirst();
        TabViewController tabController;
        if (option.isPresent()) {
            tabController = option.get();
        }
        else {
            tabController = (TabViewController) GUILoadStyleUtility
                    .generateGUIPane(GUIPanePath.EDITOR_TAB_VIEW.getFilePath());
            attachChildContainers(tabController);
            try {
                tabController.setLevel(levelID);
                tabController.modelUpdate();
            }
            catch (LevelNotFoundException | CampaignNotFoundException e) {
                DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
            }
            myTabViewControllers.add(tabController);
        }
        Tab tab = new Tab(String.format("%s: %s", levelID.campaignName, levelID.levelName));
        tab.setUserData(levelID);
        tab.setContent((BorderPane) tabController.getRoot());
        tabPane.getTabs().add(tab);
        return tab;
    }

    @Override
    public void init () {
        myTabViewControllers = new ArrayList<>();
        attachChildContainers(editorMenuBarController, levelElementAccordionController);
        initProjectExplorer();
        initInfoBox();
        editorMenuBarController.attachScreen(this);
    }

    @Override
    public void modelUpdate () {
        updateProjectExplorer();
        updateTabTexts();
        updateInfoBox(projectExplorerController.getSelectedHierarchy());
    }

    private void updateTabTexts () {
        tabPane.getTabs().forEach( (tab) -> {
            LevelIdentifier id = (LevelIdentifier) tab.getUserData();
            tab.setText(String.format("%s: %s", id.campaignName, id.levelName));
        });
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
    
}
