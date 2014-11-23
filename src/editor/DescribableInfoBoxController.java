package editor;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.GUIController;


/**
 * 
 * @author Jonathan Tseng
 *
 */
public class DescribableInfoBoxController implements GUIController {

    private SimpleStringProperty myIconButtonText = new SimpleStringProperty("Load Icon");

    @FXML
    private VBox infoRoot;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ImageView iconImageView;
    @FXML
    private Label iconLabel;
    @FXML
    private Button iconFileChooserButton;

    /**
     * 
     * @param name
     * @param description
     * @param iconFilePath
     * @param icon
     */
    public void setInfo (String name, String description, String iconFilePath, Image icon) {
        nameTextField.setText(name);
        ;
        descriptionTextArea.setText(description);
        iconLabel.setText(iconFilePath);
        iconImageView.setImage(icon);
    }

    private void initIconFileChoosing () {
        iconFileChooserButton.textProperty().bind(myIconButtonText);
        iconFileChooserButton.setOnAction(e -> System.out.println("clicked!"));
    }

    private void initLabelInputSwitching () {
        setEditableToggle(nameTextField);
        setEditableToggle(descriptionTextArea);
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
        initIconFileChoosing();
        initLabelInputSwitching();
    }

}
