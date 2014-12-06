package view.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.LevelState;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import view.dialog.DialogBoxUtility;
import view.gui.GUIScreen;


/**
 * Game Runner Screen, holds a Game Runner View Node
 * 
 * @author Jonathan Tseng
 *
 */
public class RunnerScreen extends GUIScreen {

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

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    protected void init () {
        // sizedButton used for sizing other panes and as background in stackpane
        sizedButton.setStyle("-fx-background-color: black;");
        sizedButton.disarm();
        attachChildContainers(runnerMenuBarController, gameRunnerController);
        runnerMenuBarController.attachScreen(this);
        gameRunner.prefHeightProperty().bind(sizedButton.heightProperty());
        gameRunner.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooser.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooser.prefHeightProperty().bind(sizedButton.heightProperty());
        levelChooserController.setOnSubmit((String campaign, String level)->playLevel(campaign, level));
        setFront(levelChooser);
    }
    
    private void playLevel(String campaign, String level) {
        try {
            LevelState levelState = myMainModel.getLevel(campaign, level);
            gameRunnerController.setLevel(levelState);
            setFront(gameRunner);
        }
        catch (LevelNotFoundException | CampaignNotFoundException e) {
            // TODO multilanguage
            DialogBoxUtility.createMessageDialog("Failed to load level");
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
    public void update () {
        updateLevelChooser();
    }

}
