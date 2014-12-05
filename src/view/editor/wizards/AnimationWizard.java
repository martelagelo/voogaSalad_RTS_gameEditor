package view.editor.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import engine.visuals.elementVisuals.animations.AnimationTag;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class AnimationWizard extends Wizard {

    private final static String START_FRAME_KEY = "StartFrame";
    private final static String STOP_FRAME_KEY = "StopFrame";

    @FXML
    private AnchorPane leftPane;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private ComboBox<AnimationTag> animationTag;
    @FXML
    private TextField startFrame;
    @FXML
    private TextField stopFrame;
    @FXML
    private CheckBox animationRepeat;
    @FXML
    private TextField slownessMultiplier;
    
    private ImageView imageView;
    private AnimationGrid animationGrid;
    private String imagePath; 
    private Double frameWidth;
    private Double frameHeight;   
    
    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
    private void loadImage () {
        File file = new File(imagePath);
        try {
            spritesheet.getChildren().clear();
            Image image = new Image(new FileInputStream(file));
            imagePath = file.getPath();
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);
            animationGrid =
                    new AnimationGrid(image.getWidth(), image.getHeight(), frameWidth.doubleValue(),
                                      frameHeight.doubleValue());
            spritesheet.getChildren().add(animationGrid);
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
        imagePath = "";
        attachTextProperties();
        errorMessage.setFill(Paint.valueOf("white"));
        animationTag.setItems(FXCollections.observableList(Arrays.asList(AnimationTag.values())));
        startFrame.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {                
                if (Pattern.matches(NUM_REGEX, newValue)) {
                    int start = Integer.parseInt(newValue);
                    try {
                        animationGrid.setStart(start);
                    }
                    catch (IndexOutOfBoundsException e) {
                        displayErrorMessage(e.getMessage());
                    }
                }
            }
        });
        
        stopFrame.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable,
                                 String oldValue,
                                 String newValue) {                
                if (Pattern.matches(NUM_REGEX, newValue)) {
                    int stop = Integer.parseInt(newValue);
                    try {
                        animationGrid.setStop(stop);
                    }
                    catch (IndexOutOfBoundsException e) {
                        displayErrorMessage(e.getMessage());
                    }
                }
            }
        });
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
            startFrame.promptTextProperty().bind(util.getStringProperty(START_FRAME_KEY));
            stopFrame.promptTextProperty().bind(util.getStringProperty(STOP_FRAME_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }    

    @Override
    public boolean checkCanSave () {
        return !startFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, startFrame.getText()) && 
               !stopFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, stopFrame.getText()) &&
               animationTag.getSelectionModel().getSelectedItem() != null &&
               !slownessMultiplier.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, slownessMultiplier.getText());
    }

    @Override
    public void updateData () {       
        setWizardType(WizardType.ANIMATION_SEQUENCE);
        addToData(WizardDataType.ANIMATION_TAG, animationTag.getSelectionModel().getSelectedItem().name());
        addToData(WizardDataType.START_FRAME, startFrame.getText());
        addToData(WizardDataType.STOP_FRAME, stopFrame.getText());
        addToData(WizardDataType.ANIMATION_REPEAT, Boolean.toString(animationRepeat.isSelected()));
        addToData(WizardDataType.SLOWNESS_MULTIPLIER, slownessMultiplier.getText());
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        // TODO implement this!
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        imagePath = values.get(0);
        frameWidth = Double.parseDouble(values.get(1));
        frameHeight = Double.parseDouble(values.get(2));
        loadImage();
    }

}