package editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.WizardUtility;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class DrawableGameElementWizard extends Wizard {

    private static final String NUM_REGEX = "-?[0-9]+\\.?[0-9]*";
    private static final String NUM_ATTR_WIZARD =
            "/editor/wizards/guipanes/NumberAttributeWizard.fxml";
    private static final String STRING_ATTR_WIZARD =
            "/editor/wizards/guipanes/StringAttributeWizard.fxml";
    private static final String TRIGGER_WIZARD = "/editor/wizards/guipanes/TriggerWizard.fxml";
    @FXML
    private TextField name;
    @FXML
    private Button trigger;
    @FXML
    private Button stringAttribute;
    @FXML
    private Button numberAttribute;
    @FXML
    private Button image;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private Slider frameWidth;
    @FXML
    private TextField frameWidthText;
    @FXML
    private Slider frameHeight;
    @FXML
    private TextField frameHeightText;
    @FXML
    private TextField numRows;
    @FXML
    private TextField startFrame;
    @FXML
    private TextField stopFrame;
    @FXML
    private CheckBox animationRepeat;
    
    
    private ImageView imageView;
    private GridLines gridLines;
    private String imagePath;

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchTriggerEditor () {
        launchNestedWizard(TRIGGER_WIZARD);
    }

    /**
     * Launches a String Attribute Wizard
     * 
     */
    private void launchStringAttributeEditor () {
        launchNestedWizard(STRING_ATTR_WIZARD);
    }

    /**
     * Launches a Nuumber Attribute Wizard
     * 
     */
    private void launchNumberAttributeEditor () {
        launchNestedWizard(NUM_ATTR_WIZARD);
    }

    private void launchNestedWizard (String s) {
        Wizard wiz = WizardUtility.loadWizard(s, new Dimension(600, 300));
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);
            wiz.getStage().close();
        };
        wiz.setSubmit(bc);
    }

    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
    private void loadImage () {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            spritesheet.getChildren().clear();
            Image image = new Image(new FileInputStream(file));
            imagePath = file.getPath();
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);

            frameWidth.setMin(20.0);
            frameWidth.setMax(image.getWidth());
            frameWidth.setValue(100.0);
            frameHeight.setMin(20.0);
            frameHeight.setMax(image.getHeight());
            frameHeight.setValue(100.0);

            gridLines =
                    new GridLines(image.getWidth(), image.getHeight(), frameWidth.getValue(),
                                  frameHeight.getValue());
            spritesheet.getChildren().add(gridLines);
        }
        catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Invalid Image");
        }
    }

    /**
     * Binds all the listeners to the scene
     * 
     */
    @Override
    public void initialize () {
        super.initialize();
        trigger.setOnAction(e -> launchTriggerEditor());
        stringAttribute.setOnAction(e -> launchStringAttributeEditor());
        numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
        image.setOnAction(i -> loadImage());
        createSliderListeners();
        createTextFieldListeners();
        imagePath = "";
    }

    private void createTextFieldListeners () {
        frameWidthText.textProperty().addListener(e -> {
            if (Pattern.matches(NUM_REGEX, frameWidthText.getText())) {
                frameWidth.setValue(Double.parseDouble(frameWidthText.getText()));
            }
        });
        frameHeightText.textProperty().addListener(e -> {
            if (Pattern.matches(NUM_REGEX, frameHeightText.getText())) {
                frameHeight.setValue(Double.parseDouble(frameHeightText.getText()));
            }
        });
    }

    private void createSliderListeners () {
        frameWidth.valueProperty().addListener(e -> {
            frameWidthText.setText("" + (int) frameWidth.getValue());
            if (gridLines != null) {
                gridLines.changeSize(frameWidth.getValue(), gridLines.getFrameY());
            }
        });
        frameHeight.valueProperty().addListener(e -> {
            frameHeightText.setText("" + (int) frameHeight.getValue());
            if (gridLines != null) {
                gridLines.changeSize(gridLines.getFrameX(), frameHeight.getValue());
            }
        });
    }

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty() && imageView != null && !numRows.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, numRows.getText()) && !startFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, startFrame.getText()) && !stopFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, stopFrame.getText());
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.DRAWABLE_GAME_ELEMENT);
        addToData(WizardDataType.NAME, name.getText());
        addToData(WizardDataType.IMAGE, imagePath);
        addToData(WizardDataType.WIDTH, "" + imageView.getImage().getWidth());
        addToData(WizardDataType.HEIGHT, "" + imageView.getImage().getHeight());
        addToData(WizardDataType.FRAME_X, "" + (int) frameWidth.getValue());
        addToData(WizardDataType.FRAME_Y, "" + (int) frameHeight.getValue());
        addToData(WizardDataType.ROWS, numRows.getText());
        addToData(WizardDataType.START_FRAME, startFrame.getText());
        addToData(WizardDataType.STOP_FRAME, stopFrame.getText());
        addToData(WizardDataType.ANIMATION_REPEAT, Boolean.toString(animationRepeat.isSelected()));        
    }

}
