package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javafx.scene.control.TextArea;
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
    private TextArea title;

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
        drawTitle();
        newGameButton.setOnAction(e -> {
            myMainModel.newGame();
            switchScreen(ViewScreen.EDITOR);
        });

        // TODO we need to link this up with save load in MainView and MainModel
        launchEditorButton.setOnAction(e -> {
            if (gameDropDown.getSelectionModel().getSelectedItem() != null) {
                try {
                    myMainModel.loadGame(gameDropDown.getSelectionModel().getSelectedItem());
                } catch (Exception e1) {
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
                switchScreen(ViewScreen.EDITOR);
            });
        attachStringProperties();
    }
    
    private void drawTitle () {
    	EventHandler<ActionEvent> action;
			action = new EventHandler<ActionEvent>(){
				Scanner myScanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("resources/duvall.txt"));
				String contents = "";
				public void handle(ActionEvent t) {
					if(myScanner.hasNextLine()){
						title.setPrefRowCount(1000);
						title.setPrefColumnCount(60);
						contents+=myScanner.nextLine();
						contents+="\n";
						title.setText(contents);
					}
				}
			};
			KeyFrame keyFrame = new KeyFrame(new Duration(50), action);
			Timeline gameLoop = new Timeline(keyFrame);
			gameLoop.setCycleCount(Animation.INDEFINITE);
			gameLoop.play();
    	
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
