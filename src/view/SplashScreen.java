package view;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
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


public class SplashScreen extends GUIScreen {

    
    @FXML private GridPane splash;
    @FXML private Button launchEditorButton;
    @FXML private Button launchRunnerButton;
    @FXML private ComboBox<String> myGames;
    private static final String LAUNCH_EDITOR = "Launch Editor";
    private static final String LAUNCH_RUNNER = "Launch Runner";
    private static final String GAMES_DIRECTORY = "./myGames/";
    private static final String NEW_GAME = "New Game";

    @Override
    public Node getRoot () {
        return splash;
    }

    @Override
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
        gameNames.add(0, NEW_GAME);
        myGames.setItems(FXCollections.observableArrayList(gameNames));
        
        //TODO we need to link this up with save load in MainView and MainModel
        launchEditorButton.setOnAction(e -> {
            if (myGames.getSelectionModel().getSelectedItem() != null) {
                switchScreen(ViewScreen.EDITOR, myGames.getSelectionModel().getSelectedItem());
            }
            else {
                //TODO: ERROR POPUP ON SPLASH SCREEN, nishad... animation controller thingy
                System.out.println("ERROR: NO GAME SELECTED");
            }
            
        });
        
        //TODO Change to ViewScreen.RUNNER, also fill in String game (2nd argument)
        launchRunnerButton.setOnAction(e -> switchScreen(ViewScreen.EDITOR, ""));          
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

}
