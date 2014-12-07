package view.editor.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
import javafx.scene.text.Text;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import engine.visuals.elementVisuals.animations.AnimationTag;
import engine.visuals.elementVisuals.animations.AnimationTag.AnimationType;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class AnimationWizard extends Wizard {

    private final static String GRID_PROMPT_KEY = "DirectionGridPrompt";
    private final static String ANIMATION_ACTION_KEY = "AnimationAction";
    private final static String START_FRAME_KEY = "StartFrame";
    private final static String STOP_FRAME_KEY = "StopFrame";
    
    private final int DIRECTION_GRID_SIZE = 50;

    @FXML
    private AnchorPane leftPane;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private ComboBox<AnimationTag> animationAction;
    @FXML
    private Group animationDirection;
    @FXML
    private Text gridPrompt;
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
    private DirectionGrid directionGrid;
    private String imagePath;
    private String colorMaskPath;
    private Double frameWidth;
    private Double frameHeight;

    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
    private void loadImage () {
        File imageFile = new File(imagePath);
        File colorMaskFile = new File(colorMaskPath);
        try {
            spritesheet.getChildren().clear();
            Image image = addImage(imageFile);
            Image colorMask = addImage(colorMaskFile);            
            
            animationGrid =
                    new AnimationGrid(image.getWidth(), image.getHeight(),
                                      frameWidth.doubleValue(),
                                      frameHeight.doubleValue());
            spritesheet.getChildren().add(animationGrid);
        }
        catch (Exception e) {
            displayErrorMessage("Unable to Load Image");
        }
    }

    private Image addImage (File imageFile) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageFile));
        imagePath = imageFile.getPath();
        imageView = new ImageView(image);
        spritesheet.getChildren().add(imageView);
        return image;
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
        animationAction.setItems(FXCollections.observableList(Arrays.asList(AnimationTag.values())
                .stream().filter(tag -> tag.getType().equals(AnimationType.ACTION))
                .collect(Collectors.toList())));

        startFrame.textProperty().addListener( (observable, oldValue, newValue) -> {
            updateAnimationGrid( (frame) -> animationGrid.setStart(frame), newValue);
        });
        stopFrame.textProperty().addListener( (observable, oldValue, newValue) -> {
            updateAnimationGrid( (frame) -> animationGrid.setStop(frame), newValue);
        });
        gridPrompt.setFill(Paint.valueOf("white"));
        directionGrid = new DirectionGrid(DIRECTION_GRID_SIZE, DIRECTION_GRID_SIZE);
        animationDirection.getChildren().add(directionGrid);
    }

    private void updateAnimationGrid (Consumer<Integer> updateCons, String newValue) {
        if (Pattern.matches(NUM_REGEX, newValue)) {
            int intValue = Integer.parseInt(newValue);
            try {
                updateCons.accept(intValue);
            }
            catch (IndexOutOfBoundsException e) {
                displayErrorMessage(e.getMessage());
            }
        }
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
            gridPrompt.textProperty().bind(util.getStringProperty(GRID_PROMPT_KEY));
            animationAction.promptTextProperty().bind(util.getStringProperty(ANIMATION_ACTION_KEY));
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
               animationAction.getSelectionModel().getSelectedItem() != null &&
               !slownessMultiplier.getText().isEmpty() &&
               Pattern.matches(NUM_REGEX, slownessMultiplier.getText()) &&
               directionGrid.getDirections().size() > 0;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.ANIMATION_SEQUENCE);
        addToData(WizardDataType.ANIMATION_TAG, getTags());
        addToData(WizardDataType.START_FRAME, startFrame.getText());
        addToData(WizardDataType.STOP_FRAME, stopFrame.getText());
        addToData(WizardDataType.ANIMATION_REPEAT, Boolean.toString(animationRepeat.isSelected()));
        addToData(WizardDataType.SLOWNESS_MULTIPLIER, slownessMultiplier.getText());
    }

    private String getTags () {
        StringBuilder sb = new StringBuilder();
        for (AnimationTag tag : directionGrid.getDirections()) {
            sb.append(tag.name() + ",");
        }
        sb.append(animationAction.getSelectionModel().getSelectedItem().name());
        return sb.toString();
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        startFrame.setText(oldValues.getValueByKey(WizardDataType.START_FRAME));
        stopFrame.setText(oldValues.getValueByKey(WizardDataType.STOP_FRAME));
        animationRepeat.setSelected(Boolean.parseBoolean(oldValues
                .getValueByKey(WizardDataType.ANIMATION_REPEAT)));
        slownessMultiplier.setText(oldValues.getValueByKey(WizardDataType.SLOWNESS_MULTIPLIER));
        List<AnimationTag> tags =
                Arrays.asList(oldValues.getValueByKey(WizardDataType.ANIMATION_TAG).split(","))
                        .stream().map(tag -> AnimationTag.valueOf(tag))
                        .collect(Collectors.toList());
        selectCorrectFrame(tags);
        animationAction.getSelectionModel()
                .select(tags.stream().filter(tag -> tag.getType().equals(AnimationType.ACTION))
                        .collect(Collectors.toList()).get(0));
    }

    private void selectCorrectFrame (List<AnimationTag> tags) {
        int row = 1;
        int col = 1;
        if (tags.contains(AnimationTag.FORWARD))
            row = 2;
        else if (tags.contains(AnimationTag.BACKWARD)) row = 0;
        if (tags.contains(AnimationTag.LEFT))
            col = 0;
        else if (tags.contains(AnimationTag.RIGHT)) col = 2;
        directionGrid.selectFrame(row, col);
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        imagePath = values.get(0);
        colorMaskPath = values.get(1);
        frameWidth = Double.parseDouble(values.get(2));
        frameHeight = Double.parseDouble(values.get(3));
        loadImage();
    }

}
