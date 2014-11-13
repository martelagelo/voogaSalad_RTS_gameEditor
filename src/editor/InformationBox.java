package editor;

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
    
    @FXML private VBox infoRoot;
    @FXML private Label nameLabel;
    @FXML private TextField nameTextField;
    @FXML private Label descriptionLabel;
    @FXML private TextArea descriptionTextArea;
    @FXML private ImageView iconImageView;
    @FXML private Label iconLabel;
    @FXML private Button iconFileChooser;

    @Override
    public Node getRoot () {
        return infoRoot;
    }

    @Override
    public String[] getCSS () {
        return new String[0];
    }

    @Override
    @FXML
    public void initialize () {
        System.out.println("initializing information view");
        
    }

}
