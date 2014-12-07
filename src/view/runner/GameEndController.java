package view.runner;

import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIController;


/**
 * Controller for showing the game ending
 * 
 * @author Jonathan Tseng
 *
 */
public class GameEndController implements GUIController {

    private static final String DONE_KEY = "Done";

    @FXML
    private BorderPane root;
    @FXML
    private Label title;
    @FXML
    private VBox attributeList;
    @FXML
    private Button doneButton;

    public void updateGameEndView (String titleString, List<String> attributes) {
        title.setText(titleString);
        attributeList.getChildren().clear();
        attributes.forEach( (attribute) -> {
            Label attributeLabel = new Label(attribute);
            attributeLabel.setAlignment(Pos.CENTER);
            attributeLabel.setMaxWidth(Double.MAX_VALUE);
            attributeList.getChildren().add(attributeLabel);
        });
    }

    public void setOnDone (EventHandler<ActionEvent> event) {
        doneButton.setOnAction(event);
    }

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    public void initialize () {
        try {
            doneButton.textProperty().bind(MultiLanguageUtility.getInstance()
                                                   .getStringProperty(DONE_KEY));
        }
        catch (LanguagePropertyNotFoundException e) {
            DialogBoxUtility.createMessageDialog(Arrays.toString(e.getStackTrace()));
        }
    }

}
