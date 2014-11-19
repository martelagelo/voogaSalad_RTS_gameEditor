package view;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * 
 * @author Jonathan
 * @author Nishad
 * @author Joshua
 *
 */


public class SplashScreen extends GUIScene implements GUIController {

    @FXML private GridPane splash;
    @FXML private Button launchEditorButton;
    @FXML private Button launchRunnerButton;
    @FXML private ComboBox<String> myGames;
    private static final String LAUNCH_EDITOR = "Launch Editor";
    private static final String LAUNCH_RUNNER = "Launch Runner";
    private static final String GAMES_DIRECTORY = "./myGames/";

    @Override
    @FXML
    public void initialize () {
        launchEditorButton.setText(LAUNCH_EDITOR);
        launchRunnerButton.setText(LAUNCH_RUNNER);
        
        File folder = new File(GAMES_DIRECTORY);
        List<File> files = Arrays.asList(folder.listFiles());
        List<String> gameNames = files
        		.stream()
        		.filter(f -> f.isDirectory())
        		.map(f -> f.getName())
        		.collect(Collectors.toList());   
        myGames.setItems(FXCollections.observableArrayList(gameNames));
        
        launchEditorButton.setOnAction(e -> switchScene(ViewScreen.EDITOR, ""));
        //TODO Change to ViewScreen.RUNNER, also fill in String game (2nd argument)
        launchRunnerButton.setOnAction(e -> switchScene(ViewScreen.EDITOR, ""));
    }


    @Override
    public String[] getCSS () {
        return new String[] { "/view/stylesheets/splash.css" };
    }

    @Override
    public Node getRoot () {
        return splash;
    }

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }

}
