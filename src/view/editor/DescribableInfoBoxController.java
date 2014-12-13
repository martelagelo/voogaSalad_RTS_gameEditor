package view.editor;

import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.gui.GUIController;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class DescribableInfoBoxController implements GUIController {

    // private SimpleStringProperty myIconButtonText = new SimpleStringProperty("Load Icon");
    private static final String SUBMIT_KEY = "Update";
    private BiConsumer<String, String> mySubmitConsumer = (String name, String description) -> {
    };

    @FXML
    private VBox infoRoot;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button submitButton;

    private void initLabelInputSwitching () {
        setEditableToggle(nameTextField);
        setEditableToggle(descriptionTextArea);
    }

    public void setSubmitAction (BiConsumer<String, String> textChangedAction) {
        mySubmitConsumer = textChangedAction;
    }

    public void setText (String name, String description) {
        nameTextField.setText(name);
        descriptionTextArea.setText(description);
    }

    private void setEditableToggle (TextInputControl textInputControl) {
        textInputControl.setEditable(false);
        textInputControl.setOnMouseClicked(e -> textInputControl.setEditable(true));
        textInputControl.focusedProperty().addListener( (e, oldVal, newVal) -> textInputControl
                .setEditable(newVal));
    }

    @Override
    public Node getRoot () {
        return infoRoot;
    }

    @Override
    public void initialize () {
        initLabelInputSwitching();
        try {
            submitButton.textProperty().bind(MultiLanguageUtility.getInstance()
                                                     .getStringProperty(SUBMIT_KEY));
        }
        catch (LanguagePropertyNotFoundException e1) {
            e1.printStackTrace();
        }
        submitButton.setOnAction(e -> mySubmitConsumer.accept(nameTextField.getText(),
                                                              descriptionTextArea.getText()));
    }

}
