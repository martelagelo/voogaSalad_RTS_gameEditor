package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.gui.GUIPanePath;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class DrawableGameElementWizard extends Wizard {

    private final static String NAME_KEY = "Name";
    private final static String NEW_ACTION_KEY = "NewAction";
    private final static String NEW_STRING_ATTRIBUTE_KEY = "NewStringAttribute";
    private final static String NEW_NUMBER_ATTRIBUTE_KEY = "NewNumberAttribute";
    private final static String ADD_ANIMATION_KEY = "AddAnimation";
    private final static String SET_BOUNDS_KEY = "SetBounds";
    private final static String LOAD_IMAGE_KEY = "LoadImage";
    private final static String FRAME_HEIGHT_KEY = "FrameHeight";
    private final static String FRAME_WIDTH_KEY = "FrameWidth";

    @FXML
    private AnchorPane leftPane;
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
    private Label frameWidthLabel;
    @FXML
    private Slider frameWidth;
    @FXML
    private TextField frameWidthText;
    @FXML
    private Label frameHeightLabel;
    @FXML
    private Slider frameHeight;
    @FXML
    private TextField frameHeightText;
    @FXML
    private Button animation;
    @FXML
    private VBox existingTriggers;
    @FXML
    private VBox existingStringAttributes;
    @FXML
    private VBox existingNumberAttributes;
    @FXML
    private VBox existingAnimations;
    @FXML
    private Button setBounds;

    private List<String> myGlobalStringAttributes;
    private List<String> myGlobalNumberAttributes;
    private ImageView imageView;
    private AnimationGrid animationGrid;
    private String imagePath;

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchTriggerEditor () {
        launchNestedWizard(GUIPanePath.ACTION_WIZARD, existingTriggers, myGlobalNumberAttributes,
                           new Dimension(400, 600));
    }

    /**
     * Launches a String Attribute Wizard
     * 
     */
    private void launchStringAttributeEditor () {
        launchNestedWizard(GUIPanePath.STRING_ATTRIBUTE_WIZARD, existingStringAttributes,
                           myGlobalStringAttributes, new Dimension(300, 300));
    }

    /**
     * Launches a Number Attribute Wizard
     * 
     */
    private void launchNumberAttributeEditor () {
        launchNestedWizard(GUIPanePath.NUMBER_ATTRIBUTE_WIZARD, existingNumberAttributes,
                           myGlobalNumberAttributes, new Dimension(300, 300));
    }

    private void launchAnimationEditor () {
        if (imageView != null) {
            List<String> imageValues = new ArrayList<>();
            imageValues.add(imagePath);
            imageValues.add(Double.toString(frameWidth.getValue()));
            imageValues.add(Double.toString(frameHeight.getValue()));
            launchNestedWizard(GUIPanePath.ANIMATION_WIZARD, existingAnimations,
                               imageValues, new Dimension(800, 600));
        }
        else {
            displayErrorMessage("Can't launch due to unspecified image information");
        }
    }

    private void launchBoundsEditor () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.BOUNDS_WIZARD, new Dimension(500, 700));
        if (this.getWizardData().getWizardDataByType(WizardType.BOUNDS).size() != 0) {
            wiz.launchForEdit(this.getWizardData().getWizardDataByType(WizardType.BOUNDS).get(0));
        }
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);
            wiz.closeStage();
        };
        wiz.setSubmit(bc);
    }

    private void launchNestedWizard (GUIPanePath path,
                                     VBox existing,
                                     List<String> globalAttrs,
                                     Dimension dim) {
        Wizard wiz = WizardUtility.loadWizard(path, dim);
        wiz.loadGlobalValues(globalAttrs);
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);
            HBox newElement = new HBox();
            Button edit = new Button();
            //TODO: Fix the text that goes into the button           
            edit.setText((new ArrayList<String>(data.getData().values())).get(0));
            edit.setOnAction(e -> launchEditWizard(path, data, edit, globalAttrs, dim));
            newElement.getChildren().add(edit);

            Button delete = new Button();
            delete.setText("X");
            delete.setOnAction(e -> {
                removeWizardData(data);
                existing.getChildren().remove(newElement);
            });
            newElement.getChildren().add(delete);
            existing.getChildren().add(newElement);

            wiz.closeStage();
        };
        wiz.setSubmit(bc);
    }

    private void launchEditWizard (GUIPanePath path,
                                   WizardData oldData,
                                   Button button,
                                   List<String> globalAttrs, Dimension dim) {
        Wizard wiz = WizardUtility.loadWizard(path, dim);
        wiz.loadGlobalValues(globalAttrs);
        wiz.launchForEdit(oldData);
        Consumer<WizardData> bc = (data) -> {
            removeWizardData(oldData);
            addWizardData(data);
            oldData.clear();
            for (WizardDataType type : data.getData().keySet()) {
                oldData.addDataPair(type, data.getValueByKey(type));
            }
            button.setText((new ArrayList<String>(data.getData().values())).get(0));
            wiz.closeStage();
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
            spritesheet.setOnMouseClicked(imageScroll.getOnMouseClicked());
            spritesheet.getChildren().clear();
            Image image = new Image(new FileInputStream(file));
            imagePath = file.getPath();
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);

            frameWidth.setMin(10.0);
            frameWidth.setMax(image.getWidth());
            frameWidth.setValue(100.0);
            frameHeight.setMin(10.0);
            frameHeight.setMax(image.getHeight());
            frameHeight.setValue(100.0);

            animationGrid =
                    new AnimationGrid(image.getWidth(), image.getHeight(), frameWidth.getValue(),
                                      frameHeight.getValue());
            spritesheet.getChildren().add(animationGrid);
            spritesheet.toFront();
        }
        catch (Exception e) {
            displayErrorMessage("Unable to Load Image");
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
        animation.setOnAction(e -> launchAnimationEditor());
        setBounds.setOnAction(e -> launchBoundsEditor());
        image.setOnAction(i -> loadImage());
        createSliderListeners();
        createTextFieldListeners();
        imagePath = "";
        errorMessage.setFill(Paint.valueOf("white"));        
    }

    /**
     * Attaches multilanguage utility to text
     * in the wizard
     * 
     */
    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            name.promptTextProperty().bind(util.getStringProperty(NAME_KEY));
            trigger.textProperty().bind(util.getStringProperty(NEW_ACTION_KEY));
            stringAttribute.textProperty().bind(util.getStringProperty(NEW_STRING_ATTRIBUTE_KEY));
            numberAttribute.textProperty().bind(util.getStringProperty(NEW_NUMBER_ATTRIBUTE_KEY));
            image.textProperty().bind(util.getStringProperty(LOAD_IMAGE_KEY));
            animation.textProperty().bind(util.getStringProperty(ADD_ANIMATION_KEY));
            setBounds.textProperty().bind(util.getStringProperty(SET_BOUNDS_KEY));
            frameHeightLabel.textProperty().bind(util.getStringProperty(FRAME_HEIGHT_KEY));
            frameWidthLabel.textProperty().bind(util.getStringProperty(FRAME_WIDTH_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            // TODO Do something useful with this exception
        }
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
            if (animationGrid != null) {
                animationGrid.changeSize(frameWidth.getValue(), animationGrid.getFrameY());
            }
        });
        frameHeight.valueProperty().addListener(e -> {
            frameHeightText.setText("" + (int) frameHeight.getValue());
            if (animationGrid != null) {
                animationGrid.changeSize(animationGrid.getFrameX(), frameHeight.getValue());
            }
        });
    }

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty() && imageView != null &&
               getWizardData().getWizardDataByType(WizardType.BOUNDS).size() != 0;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.DRAWABLE_GAME_ELEMENT);
        addToData(WizardDataType.NAME, name.getText());
        addToData(WizardDataType.IMAGE, imagePath);
        addToData(WizardDataType.FRAME_X, "" + (int) frameWidth.getValue());
        addToData(WizardDataType.FRAME_Y, "" + (int) frameHeight.getValue());
        addToData(WizardDataType.COLS, Integer.toString(animationGrid.getNumColumns()));
    }

    public void attachStringAttributes (List<String> stringAttributes) {
        myGlobalStringAttributes = stringAttributes;
    }

    public void attachNumberAttributes (List<String> numberAttributes) {
        myGlobalNumberAttributes = numberAttributes;
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        // TODO implement this!
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing
    }

}
