package view.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.LevelState;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIScreen;


/**
 * Game Runner Screen, holds a Game Runner View Node
 * 
 * @author Jonathan Tseng
 *
 */
public class RunnerScreen extends GUIScreen {

    private static final String LOAD_LEVEL_ERROR_KEY = "LoadLevelError";

    @FXML
    private BorderPane root;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button sizedButton;
    @FXML
    private StackPane gameRunner;
    @FXML
    private GameRunnerPaneController gameRunnerController;
    @FXML
    private BorderPane levelChooser;
    @FXML
    private LevelChooserController levelChooserController;
    @FXML
    private MenuBar runnerMenuBar;
    @FXML
    private RunnerMenuBarController runnerMenuBarController;

    private LevelState myLevel;

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    protected void init () {
        runnerMenuBarController.attachScreen(this);
        // sizedButton used for sizing other panes and as background in stackpane
        sizedButton.setStyle("-fx-background-color: white;");
        sizedButton.disarm();
        attachChildContainers(runnerMenuBarController, gameRunnerController);
        gameRunner.prefHeightProperty().bind(sizedButton.heightProperty());
        gameRunner.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooser.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooser.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooserController.setOnSubmit( (String campaign, String level) -> playLevel(campaign,
                                                                                         level));
        gameRunnerController.setOnDone(e -> setFront(levelChooser));
        setFront(levelChooser);
    }

    private void playLevel (String campaign, String level) {
        try {
            myLevel = myMainModel.getLevel(campaign, level);
            gameRunnerController.setLevel(myLevel);
            gameRunnerController.addObserver(this);
            setFront(gameRunner);
        }
        catch (LevelNotFoundException | CampaignNotFoundException e) {
            try {
                DialogBoxUtility.createMessageDialog(MultiLanguageUtility.getInstance()
                        .getStringProperty(LOAD_LEVEL_ERROR_KEY).getValue());
            }
            catch (LanguagePropertyNotFoundException e1) {
                DialogBoxUtility.createMessageDialog(e1.toString());
            }
        }

    }

    private void setFront (Node child) {
        stackPane.getChildren().remove(sizedButton);
        stackPane.getChildren().add(sizedButton);
        stackPane.getChildren().remove(child);
        stackPane.getChildren().add(child);
    }

    private void updateLevelChooser () {
        Map<String, List<String>> campaignLevelMap = new HashMap<>();
        myMainModel
                .getCurrentGame()
                .getCampaigns()
                .forEach( (campaignState) -> {
                    List<String> levelNames =
                            campaignState.getLevels().stream()
                                    .map( (levelState) -> levelState.getName())
                                    .collect(Collectors.toList());
                    campaignLevelMap.put(campaignState.getName(), levelNames);
                });
        levelChooserController.updateDropDowns(campaignLevelMap);
    }

    @Override
    public void modelUpdate () {
        updateLevelChooser();
    }

}
