package view.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.CampaignState;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelState;
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

    private Tab myCurrentTab;
    private final int myHashingPrime = 31;

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
            launchTab(campaign, level);
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
                                .format("Failed to update selected describable state due to: %s",
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
                    .format("Failed to update InfoBox based on project selection due to: %s",
                            e.toString()));
        }
    }

    private void initTabs () {
        tabPane
                .getSelectionModel()
                .selectedItemProperty()
                .addListener( (observable, oldTab, newTab) -> tabChanged(observable, oldTab, newTab));
    }

    private void launchTab (String campaign, String level) {
        Optional<Tab> option =
                tabPane.getTabs()
                        .stream()
                        .filter( (tab) -> ((CampaignLevelPair) tab.getUserData())
                                .equals(new CampaignLevelPair(campaign, level)))
                        .findFirst();
        Tab tab = (option.isPresent()) ? option.get() : createNewTab(campaign, level);
        tabPane.getSelectionModel().select(tab);
    }

    private Tab createNewTab (String campaign, String level) {
        Tab tab = new Tab(String.format("%s: %s", campaign, level));

        TabViewController tabController =
                (TabViewController) GUILoadStyleUtility.generateGUIPane(GUIPanePath.EDITOR_TAB_VIEW
                        .getFilePath());
        attachChildContainers(tabController);
        try {
            tabController.setLevel(campaign, level);
        }
        catch (LevelNotFoundException | CampaignNotFoundException e) {
            // Should not happen
            DialogBoxUtility.createMessageDialog(e.toString());
        }
        tab.setUserData(new CampaignLevelPair(campaign, level));
        tab.setContent((BorderPane) tabController.getRoot());
        tabPane.getTabs().add(tab);
        return tab;
    }

    private void tabChanged (ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        myCurrentTab = newTab;
        if (myCurrentTab != null) {
            CampaignLevelPair id = (CampaignLevelPair) myCurrentTab.getUserData();
            // TODO: Not sure if need anything here... nothing really to do when the tab changes
            // anymore
        }
    }

    @Override
    public void init () {
        attachChildContainers(editorMenuBarController, levelElementAccordionController);
        initTabs();
        initProjectExplorer();
        initInfoBox();
        editorMenuBarController.attachScreen(this);
    }

    @Override
    public void update () {
        updateProjectExplorer();
        updateTabTexts();
        updateInfoBox(projectExplorerController.getSelectedHierarchy());
    }

    private void updateTabTexts () {
        tabPane.getTabs().forEach( (tab) -> {
            CampaignLevelPair id = (CampaignLevelPair) tab.getUserData();
            tab.setText(String.format("%s: %s", id.myCampaign.getName(), id.myLevel.getName()));
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

    /**
     * private datastructure to differentiate between different tabs
     * 
     * @author Jonathan Tseng
     *
     */
    private class CampaignLevelPair {
        public CampaignState myCampaign;
        public LevelState myLevel;

        public CampaignLevelPair (String campaign, String level) {
            try {
                myCampaign = myMainModel.getCampaign(campaign);
                myLevel = myMainModel.getLevel(campaign, level);
            }
            catch (CampaignNotFoundException | LevelNotFoundException e) {
                // Should never happen
                DialogBoxUtility.createMessageDialog(e.toString());
            }
        }

        @Override
        public int hashCode () {
            final int prime = myHashingPrime; // this prime is magic. should be extracted.
            int result = 1;
            result = prime * result + ((myCampaign == null) ? 0 : myCampaign.getName().hashCode());
            result = prime * result + ((myLevel == null) ? 0 : myLevel.getName().hashCode());
            return result;
        }

        @Override
        public boolean equals (Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            CampaignLevelPair other = (CampaignLevelPair) obj;
            return myCampaign.getName() == other.myCampaign.getName() &&
                   myLevel.getName() == other.myLevel.getName();
        }
    }

}
