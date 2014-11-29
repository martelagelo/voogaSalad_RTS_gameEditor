package view;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;


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

    private static final String DUVALL_PATH = "resources/duvall.txt";
    // TODO make longer to scroll, 1 for now for the sake of testing
    private static final Integer LOAD_DURATION = 1;

    // TODO Probably get rid of this
    private static final String GAMES_DIRECTORY = "./myGames/";

    @FXML
    private GridPane splash;
    @FXML
    private Button launchEditorButton;
    @FXML
    private Button launchRunnerButton;
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
        // TODO: when saving a game, should specify its name to be used in splash screen rather than
        // using file folder name
        File folder = new File(GAMES_DIRECTORY);
        List<File> files = Arrays.asList(folder.listFiles());
        List<String> gameNames = files
                .stream()
                .filter(f -> f.isDirectory())
                .map(f -> f.getName())
                .collect(Collectors.toList());
        gameDropDown.setItems(FXCollections.observableArrayList(gameNames));

        setUpButtons();
        drawTitle();
    }

    private void setUpButtons () {
        newGameButton.setOnAction(e -> {
            String gameName;
            try {
                gameName = MultiLanguageUtility.getInstance().getStringProperty(DEFAULT_NEW_GAME_KEY).getValue();
            }
            catch (Exception e1) {
                // Should never happen
                gameName = "<New Game>";
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            myMainModel.newGame(gameName);
            switchScreen(ViewScreen.EDITOR);
        });
        // TODO we need to link this up with save load in MainView and MainModel
        launchEditorButton.setOnAction(e -> {
            if (gameDropDown.getSelectionModel().getSelectedItem() != null) {
                try {
                    myMainModel.loadGame(gameDropDown.getSelectionModel().getSelectedItem());
                }
                catch (Exception e1) {
                    // TODO: handle exception and display to view
                    // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            switchScreen(ViewScreen.EDITOR);
        }
                else {
                    // TODO: ERROR POPUP ON SPLASH SCREEN, nishad... animation controller thingy
                    System.out.println("ERROR: NO GAME SELECTED");
                }

            });

        // TODO Change to ViewScreen.RUNNER, also fill in String game (2nd argument)
        launchRunnerButton.setOnAction(e -> {
            // TODO Load a game
                switchScreen(ViewScreen.RUNNER);
            });
        attachStringProperties();
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
            // TODO error handling
        }
    }

    @Override
    public void update () {
        // Splash Screen has no need to update
    }

}
