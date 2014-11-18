package view;

import java.util.Observable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;



public class SplashScreen implements GUIController {

    @FXML private GridPane splash;
    @FXML private Button launchEditorButton;
    @FXML private Button launchRunnerButton;
    private static final String LAUNCH_EDITOR = "Launch Editor";
    private static final String LAUNCH_RUNNER = "Launch Runner";

    @Override
    @FXML
    public void initialize () {
        launchEditorButton.setText(LAUNCH_EDITOR);
        launchRunnerButton.setText(LAUNCH_RUNNER);
        
        launchEditorButton.setOnAction(e -> launchEditor(""));
    }
    
    private void launchEditor(String chosenGame) {
        
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
