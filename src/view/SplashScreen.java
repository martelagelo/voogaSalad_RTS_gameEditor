package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;



public class SplashScreen extends GUIScreen {

    @FXML private GridPane splash;
    @FXML private Button launchEditorButton;
    @FXML private Button launchRunnerButton;
    private static final String LAUNCH_EDITOR = "Launch Editor";
    private static final String LAUNCH_RUNNER = "Launch Runner";

    @Override
    public Node getRoot () {
        return splash;
    }

    @Override
    public void initialize () {
        launchEditorButton.setText(LAUNCH_EDITOR);
        launchRunnerButton.setText(LAUNCH_RUNNER);
        
        launchEditorButton.setOnAction(e -> switchScreen(ViewScreen.EDITOR, ""));
        //TODO Change to ViewScreen.RUNNER, also fill in String game (2nd argument)
        launchRunnerButton.setOnAction(e -> switchScreen(ViewScreen.EDITOR, ""));          
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

}
