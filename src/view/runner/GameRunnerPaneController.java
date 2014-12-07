package view.runner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.state.LevelState;
import model.state.gameelement.AttributeContainer;
import org.json.JSONException;
import util.DeepCopy;
import view.dialog.DialogBoxUtility;
import view.gui.StackPaneGUIContainer;
import engine.Engine;
import engine.visuals.ScrollablePane;


/**
 * Game Runner View Controller
 * holds scene graph as well as button pane, status pane, and minimap
 * 
 * @author Jonathan Tseng
 *
 */
public class GameRunnerPaneController extends StackPaneGUIContainer {

    @FXML
    private Button sizedButton;
    @FXML
    private BorderPane runnerPane;
    @FXML
    private BorderPane gameEnd;
    @FXML
    private GameEndController gameEndController;
    
    private LevelState myLevel;
    private Engine myEngine;
    
    @Override
    public Node getRoot () {
        return stackPane;
    }

    public void setLevel (LevelState levelState) {
        try {
            // Jank code to properly size engine runner pane to place in view
            // because JavaFX is horrible with sizing
            // require button to fill actual size of borderpane to then be bound to runner pane size
            sizedButton.setStyle("-fx-background-color: green;");
            myLevel = (LevelState) DeepCopy.deepCopy(levelState);
            myEngine = new Engine(myMainModel, myLevel);
            ScrollablePane pane = myEngine.getScene();
            bindPaneSize(pane);
            myEngine.play();
            myEngine.addObserver(this);
            runnerPane.setCenter(pane);
            setFront(runnerPane);
            pane.requestFocus();
        }
        catch (ClassNotFoundException | JSONException | IOException e) {
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }
    
    public void setInputManager(Class<?> inputManagerClass) {
        try {
            myEngine.setInputManager(inputManagerClass);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            DialogBoxUtility.createMessageDialog(e.toString());
        }
    }

    public void setOnDone(EventHandler<ActionEvent> event) {
        gameEndController.setOnDone(event);
    }
    
    @Override
    protected void init () {
        // nothing to do until level is set
    }

    @Override
    public void modelUpdate () {
        // do nothing
    }

    @Override
    public void engineUpdate (AttributeContainer attributes) {
        List<String> attributesToShow =
                attributes
                        .getNumericalAttributes()
                        .stream()
                        .map( (attribute) -> String.format("%s: %s", attribute.getName(),
                                                           attribute.getData()))
                        .collect(Collectors.toList());
        gameEndController.updateGameEndView(myLevel.getName(), attributesToShow);
        setFront(gameEnd);
    }

}
