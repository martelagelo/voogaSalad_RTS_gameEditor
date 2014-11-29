package view.dialog;

import java.util.Collection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.GUIController;


/**
 * A message dialog that shows a message with a couple of choices
 * 
 * @author Jonathan Tseng
 *
 */
public class DialogBoxController implements GUIController {

    @FXML
    private VBox root;
    @FXML
    private Label dialogMessageLabel;
    @FXML
    private HBox dialogChoicesHBox;

    public void attachInfo (Stage stage, String message, Collection<DialogChoice> choices) {        
        dialogMessageLabel.setText(message);
        choices.forEach( (dialogChoice) -> {
            Button option = new Button(dialogChoice.myLabel);
            option.setOnAction(e->{
                dialogChoice.myOnClick.handle(new ActionEvent());;
                stage.close();
            });
            dialogChoicesHBox.getChildren().add(option);
        });
    }

    @Override
    public Node getRoot () {
        return root;
    }

    @Override
    public void initialize () {

    }

}
