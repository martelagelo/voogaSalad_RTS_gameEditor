package game_editor;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;


/**
 * 
 * @author Joshua Miller
 *
 *         A class for the generation of GUIPanes based on
 *         FXML files
 */
public class GUIPaneGenerator {

    public GUIPaneGenerator () {

    }

    /**
     * A method to return the name splash page grid pane
     *
     * @return gp a GridPane of the splash page
     */
    public GridPane splashPage () throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUIPanes/SplashPage.fxml"));
        GridPane gp = new GridPane();
        gp.add(root, 0, 0);
        return gp;
    }
}
