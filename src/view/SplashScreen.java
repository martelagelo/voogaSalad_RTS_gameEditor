package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;


public class SplashScreen implements GUIController {

    @FXML
    private GridPane splash;

    @Override
    @FXML
    public void initialize () {
        System.out.println("initializing splash screen...");
    }

    @Override
    public String[] getCSS () {
        return new String[] { "/view/stylesheets/splash.css" };
    }

    @Override
    public Node getRoot () {
        return splash;
    }

}
