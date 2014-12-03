package view.editor.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import engine.visual.animation.AnimationTag;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class AnimationWizard extends Wizard {

    private final static String NUM_COLS_KEY = "NumCols";
    private final static String START_FRAME_KEY = "StartFrame";
    private final static String STOP_FRAME_KEY = "StopFrame";
    
//    public AnimationSequence (List<AnimationTag> name,
//                              int startFrame,
//                              int stopFrame,
//                              boolean repeats,
//                              double slownessMultiplier) {

    @FXML
    private AnchorPane leftPane;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private TextField numCols;
    @FXML
    private TextField startFrame;
    @FXML
    private TextField stopFrame;
    @FXML
    private CheckBox animationRepeat;
    
    private ImageView imageView;
    private AnimationGrid animationGrid;
    private String imagePath; 
    private Double frameWidth;
    private Double frameHeight;
    
    private List<String> gloabalAnimationTags; 
    
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
            setErrorMesssage("Unable to Load Image");
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
        gloabalAnimationTags =
                Arrays.asList(AnimationTag.values()).stream().map(tag -> tag.toString())
                        .collect(Collectors.toList());
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
            numCols.promptTextProperty().bind(util.getStringProperty(NUM_COLS_KEY));
            startFrame.promptTextProperty().bind(util.getStringProperty(START_FRAME_KEY));
            stopFrame.promptTextProperty().bind(util.getStringProperty(STOP_FRAME_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            setErrorMesssage(e.getMessage());
        }
    }    

    @Override
    public boolean checkCanSave () {
        return !startFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, startFrame.getText()) && !stopFrame.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, stopFrame.getText());
    }

    @Override
    public void updateData () {       
        setDataType(WizardDataType.ANIMATION_SEQUENCE);
        addToData(WizardDataType.COLS, numCols.getText());
        addToData(WizardDataType.START_FRAME, startFrame.getText());
        addToData(WizardDataType.STOP_FRAME, stopFrame.getText());
        addToData(WizardDataType.ANIMATION_REPEAT, Boolean.toString(animationRepeat.isSelected()));
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