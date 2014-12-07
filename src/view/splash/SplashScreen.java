package view.splash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.exceptions.SaveLoadException;
import util.JSONableSet;
import util.SaveLoadUtility;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIScreen;
import view.gui.ViewScreenPath;


/**
 * 
 * @author Jonathan
 * @author Nishad
 * @author Joshua
 *
 */

public class SplashScreen extends GUIScreen {

    private static final String LAUNCH_EDITOR_KEY = "LaunchEditor";
    private static final String LAUNCH_RUNNER_KEY = "LaunchRunner";
    private static final String NEW_GAME_KEY = "NewGame";
    private static final String CHOOSE_GAME_KEY = "ChooseGame";
    private static final String DEFAULT_NEW_GAME_KEY = "NewGameDefault";
    private static final String NO_GAME_SELECT_ERROR_KEY = "NoGameSelectedError";

    private static final String DUVALL_PATH = "resources/duvall.txt";
    public static final String EXISTING_GAMES =
            "src/resources/properties/view/existingGames.json";
    // TODO make longer to scroll, 1 for now for the sake of testing
    private static final Integer LOAD_DURATION = 1;

    @FXML
    private GridPane splash;
    @FXML
    private Button launchEditorButton;
    @FXML
    private Button launchRunnerButton;
    @FXML
    private ComboBox<String> myGames;

    @FXML
    private ComboBox<String> gameDropDown;
    @FXML
    private Button newGameButton;
    @FXML
    private Label title;

    @Override
    public Node getRoot () {
        return splash;
    }

    @Override
    public void init () {
        try {
            JSONableSet<String> games = SaveLoadUtility.loadResource(JSONableSet.class,
                                                                      EXISTING_GAMES);
            gameDropDown.setItems(FXCollections.observableList(new ArrayList<>(games)));
        }
        catch (SaveLoadException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }

        setUpButtons();
        drawTitle();
    }

    private void setUpButtons () {
        newGameButton.setOnAction(e -> {
            try {
                String gameName =
                        MultiLanguageUtility.getInstance().getStringProperty(DEFAULT_NEW_GAME_KEY)
                                .getValue();
                myMainModel.newGame(gameName);
                switchScreen(ViewScreenPath.EDITOR);
            }
            catch (Exception e1) {
                DialogBoxUtility.createMessageDialog(Arrays.toString(e1.getStackTrace()));
            }
        });

        launchEditorButton.setOnAction(e -> loadGameAndSwitchScreen(ViewScreenPath.EDITOR));

        launchRunnerButton.setOnAction(e -> loadGameAndSwitchScreen(ViewScreenPath.RUNNER));
        attachStringProperties();
    }

    private void loadGameAndSwitchScreen (ViewScreenPath path) {
        if (gameDropDown.getSelectionModel().getSelectedItem() != null) {
            try {
                myMainModel.loadGame(gameDropDown.getSelectionModel().getSelectedItem());
                switchScreen(path);
            }
            catch (Exception e1) {
                DialogBoxUtility.createMessageDialog(Arrays.toString(e1.getStackTrace()));
            }
        }
        else {
            try {
                DialogBoxUtility.createMessageDialog(MultiLanguageUtility.getInstance()
                        .getStringProperty(NO_GAME_SELECT_ERROR_KEY).getValue());
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void drawTitle () {
        EventHandler<ActionEvent> action;
        action = new EventHandler<ActionEvent>() {
            Scanner scanner = new Scanner(this.getClass().getClassLoader()
                    .getResourceAsStream(DUVALL_PATH));
            StringBuilder contents = new StringBuilder("");

            public void handle (ActionEvent t) {
                if (scanner.hasNextLine()) {
                    contents.append(scanner.nextLine() + "\n");
                    title.setText(contents.toString());
                }
            }
        };
        KeyFrame keyFrame = new KeyFrame(new Duration(LOAD_DURATION), action);
        Timeline timeLoop = new Timeline(keyFrame);
        timeLoop.setCycleCount(Animation.INDEFINITE);
        timeLoop.play();
    }

    private void attachStringProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            launchEditorButton.textProperty().bind(util.getStringProperty(LAUNCH_EDITOR_KEY));
            launchRunnerButton.textProperty().bind(util.getStringProperty(LAUNCH_RUNNER_KEY));
            newGameButton.textProperty().bind(util.getStringProperty(NEW_GAME_KEY));
            gameDropDown.promptTextProperty().bind(util.getStringProperty(CHOOSE_GAME_KEY));
        }
        catch (LanguageException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }

    }

    @Override
    public void modelUpdate () {
        // Splash Screen has no need to update
    }

}
