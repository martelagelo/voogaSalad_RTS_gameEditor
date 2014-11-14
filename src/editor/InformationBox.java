package editor;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import view.GUIController;


public class InformationBox implements GUIController {

    private SimpleStringProperty myIconButtonText = new SimpleStringProperty("Load Icon");
    private SimpleStringProperty myIconLabelText = new SimpleStringProperty("/{filepath}");
    
    @FXML
    private VBox infoRoot;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ImageView iconImageView;
    @FXML
    private Label iconLabel;
    @FXML
    private Button iconFileChooserButton;

    @Override
    @FXML
    public void initialize () {
        initIconFileChoosing();
        initLabelInputSwitching();
    }

    private void initIconFileChoosing () {
        iconFileChooserButton.textProperty().bind(myIconButtonText);
        iconFileChooserButton.setOnAction(e -> System.out.println("clicked!"));
        iconLabel.textProperty().bind(myIconLabelText);
    }

    private void initLabelInputSwitching () {
        nameLabel.setText("1");
        nameTextField.setEditable(false);
        
        nameLabel.setOnMouseClicked(e->{
            nameLabel.setVisible(false);
            nameTextField.setVisible(true);
            nameTextField.requestFocus();
        });
        nameTextField.setOnMouseClicked(e->{
            nameTextField.setEditable(true);
        });
        nameTextField.focusedProperty().addListener((e,oldVal,newVal)->{
            if (!newVal) {
                //nameTextField.setVisible(false);
                nameLabel.setText(nameTextField.getText());
                nameLabel.setVisible(true);
                nameTextField.setEditable(false);
            }
        });
    }

    @Override
    public Node getRoot () {
        return infoRoot;
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

}
