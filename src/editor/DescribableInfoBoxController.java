package editor;

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
import view.GUIController;


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

    // @FXML
    // private ImageView iconImageView;
    // @FXML
    // private Label iconLabel;
    // @FXML
    // private Button iconFileChooserButton;

    // public void setInfo (String name, String description, String iconFilePath, Image icon) {
    // nameTextField.setText(name);
    // ;
    // descriptionTextArea.setText(description);
    // iconLabel.setText(iconFilePath);
    // iconImageView.setImage(icon);
    // }

    // private void initIconFileChoosing () {
    // iconFileChooserButton.textProperty().bind(myIconButtonText);
    // iconFileChooserButton.setOnAction(e -> System.out.println("clicked!"));
    // }

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

    // TODO: in CSS style such that looks different depending on editable or not
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
        // initIconFileChoosing();
        initLabelInputSwitching();
        try {
            submitButton.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty(SUBMIT_KEY));
        }
        catch (LanguagePropertyNotFoundException e1) {
            e1.printStackTrace();
        }
        submitButton.setOnAction(e -> mySubmitConsumer.accept(nameTextField.getText(),
                                                              descriptionTextArea.getText()));
    }

}
