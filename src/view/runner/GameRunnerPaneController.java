package view.runner;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private BorderPane root;

    private Engine myEngine;

    @Override
    public Node getRoot () {
        return root;
    }

    private ScrollablePane myPane;

    public void setLevel (LevelState levelState) {
        try {
            myEngine = new Engine(myMainModel, levelState);
            myPane = myEngine.getScene();
            root.setCenter(myPane);
            myEngine.play();
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
