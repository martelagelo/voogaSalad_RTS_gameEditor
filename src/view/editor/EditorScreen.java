package view.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.DescribableState;
import model.state.GameState;
import model.state.LevelIdentifier;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
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

    private static final String TEAM_COLOR_PROMPT = "TeamColorPrompt";
    private static final String NEW_PARTICIPANT_KEY = "NewParticipant";

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
    @FXML
    private ColorPicker teamColorPicker;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<String> participantDropDown;

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

    private void initParticipantInfo () {
        addButton.setOnAction(e -> {
            myMainModel.addParticipantColor(teamColorPicker.getValue().toString());
        });
        participantDropDown.getSelectionModel().selectedItemProperty()
                .addListener( (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        teamColorPicker.setValue(Color.web(newValue));
                        myMainModel.setEditorChosenColor(newValue);
                    }
                });
    }

    @Override
    public void init () {
        myTabViewControllers = new ArrayList<>();
        attachChildContainers(editorMenuBarController, levelElementAccordionController);
        initProjectExplorer();
        initInfoBox();
        editorMenuBarController.attachScreen(this);
        attachTextProperties();
        initParticipantInfo();
    }

    private void attachTextProperties () {
        try {
            MultiLanguageUtility util = MultiLanguageUtility.getInstance();
            participantDropDown.promptTextProperty()
                    .bind(util.getStringProperty(TEAM_COLOR_PROMPT));
            addButton.textProperty().bind(util.getStringProperty(NEW_PARTICIPANT_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    @Override
    public void modelUpdate () {
        updateProjectExplorer();
        updateTabTexts();
        updateInfoBox(projectExplorerController.getSelectedHierarchy());
        updateParticipantDropDown();
        if (participantDropDown.getSelectionModel().selectedItemProperty().isNull().get()) {
            participantDropDown.getSelectionModel().selectFirst();
        }
    }

    private void updateParticipantDropDown () {
        participantDropDown.setItems(FXCollections.observableArrayList(new ArrayList<>(myMainModel.getGameUniverse().getParticipantColors())));
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
