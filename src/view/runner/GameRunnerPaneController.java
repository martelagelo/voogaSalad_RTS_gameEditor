package view.runner;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import model.state.LevelState;
import org.json.JSONException;
import view.dialog.DialogBoxUtility;
import view.gui.GUIContainer;
import engine.Engine;
import engine.visuals.ScrollablePane;


/**
 * Game Runner View Controller
 * holds scene graph as well as button pane, status pane, and minimap
 * 
 * @author Jonathan Tseng
 *
 */
public class GameRunnerPaneController extends GUIContainer {

    @FXML
    private StackPane root;
    @FXML
    private Button sizedButton;

    @Override
    public Node getRoot () {
        return root;
    }

    public void setLevel (LevelState levelState) {
        try {
            // Jank code to properly size engine runner pane to place in view
            // because JavaFX is horrible with sizing
            // require button to fill actual size of borderpane to then be bound to runner pane size
            sizedButton.setStyle("-fx-background-color: transparent;");
            Engine engine = new Engine(myMainModel, levelState);
            ScrollablePane pane = engine.getScene();
            pane.prefHeightProperty().bind(sizedButton.heightProperty());
            pane.prefWidthProperty().bind(sizedButton.widthProperty());
            engine.play();
            root.getChildren().add(pane);
        }
        catch (ClassNotFoundException | JSONException | IOException e) {
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }

    @Override
    protected void init () {
        // nothing to do until level is set
    }

    @Override
    public void update () {
        // do nothing for now
    }

}
