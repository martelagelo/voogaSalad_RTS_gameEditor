package view.runner;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private Button b;
    private Background b2;

    public void setLevel (LevelState levelState) {
        try {
            myEngine = new Engine(myMainModel, levelState);
            ScrollablePane pane = myEngine.getScene();

            System.out.println("init");
            b.fire();

            //root.setCenter(myEngine.getScene());
            myEngine.play();
        }
        catch (ClassNotFoundException | JSONException | IOException e) {
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }

    @Override
    protected void init () {
        // nothing to do until level is set
        b2 = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0), new Insets(0)));
        root.setBackground(b2);
        
        b = new Button("HERRO I AM DUH SOMBODI");
        b.setOnAction(e -> {
            System.out.println();
        });
        root.setTop(b);
    }

    @Override
    public void update () {
        // do nothing for now
    }

}
