package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
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
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.gui.GUIPanePath;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class GameElementWizard extends Wizard {

    private static final String UNABLE_TO_LAUNCH_ANIM =
            "Can't launch due to unspecified image information";
    private static final String UNABLE_TO_LOAD = "Unable to Load Image";
    private final static String NAME_KEY = "Name";
    private final static String NEW_ACTION_KEY = "NewAction";
    private final static String NEW_STRING_ATTRIBUTE_KEY = "NewStringAttribute";
    private final static String NEW_NUMBER_ATTRIBUTE_KEY = "NewNumberAttribute";
    private final static String NEW_WIDGET_KEY = "NewWidget";
    private final static String ADD_ANIMATION_KEY = "AddAnimation";
    private final static String SET_BOUNDS_KEY = "SetBounds";
    private final static String LOAD_IMAGE_KEY = "LoadImage";
    private final static String FRAME_HEIGHT_KEY = "FrameHeight";
    private final static String FRAME_WIDTH_KEY = "FrameWidth";
    private final static String COLOR_MASK_KEY = "ColorMask";
    private static final Dimension BOUNDS_WIZARD_SIZE = new Dimension(500, 700);
    private static final Dimension ACTION_WIZARD_SIZE = new Dimension(400, 600);
    private static final Dimension ANIMATION_WIZARD_SIZE = new Dimension(800, 800);
    private final int DEFAULT_GRID_MIN = 10;
    private final int DEFAULT_GRID_VALUE = 100;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private TextField name;
    @FXML
    private Button image;
    @FXML
    private Button colorMask;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private Label frameHeightLabel;
    @FXML
    private Slider frameWidth;
    @FXML
    private TextField frameWidthText;
    @FXML
    private Label frameWidthLabel;
    @FXML
    private Slider frameHeight;
    @FXML
    private TextField frameHeightText;
    @FXML
    private Button setBounds;
    @FXML
    private CRUDContainer stringAttributesController;
    @FXML
    private CRUDContainer numberAttributesController;
    @FXML
    private CRUDContainer triggersController;
    @FXML
    private CRUDContainer widgetsController;
    @FXML
    private CRUDContainer animationsController;
    private List<String> imageValues;
    private List<String> myGlobalStringAttributes;
    private List<String> myGlobalNumberAttributes;
    private ImageView imageView;
    private AnimationGrid animationGrid;
    private String imagePath;
    private String colorMaskPath;

    @Override
    public void initialize () {
        super.initialize();
        imagePath = "";
        colorMaskPath = "";
        imageValues = new ArrayList<>();
        myGlobalNumberAttributes = new ArrayList<>();
        myGlobalStringAttributes = new ArrayList<>();
        setBounds.setOnAction(e -> launchBoundsWizard());
        image.setOnAction(i -> loadImage());
        colorMask.setOnAction(i -> loadColorMask());
        errorMessage.setFill(Paint.valueOf("white"));
        initContainers();
    }

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            name.promptTextProperty().bind(util.getStringProperty(NAME_KEY));
            triggersController.bindButtonText(util.getStringProperty(NEW_ACTION_KEY));
            stringAttributesController.bindButtonText(util
                    .getStringProperty(NEW_STRING_ATTRIBUTE_KEY));
            numberAttributesController.bindButtonText(util
                    .getStringProperty(NEW_NUMBER_ATTRIBUTE_KEY));
            image.textProperty().bind(util.getStringProperty(LOAD_IMAGE_KEY));
            animationsController.bindButtonText(util.getStringProperty(ADD_ANIMATION_KEY));
            setBounds.textProperty().bind(util.getStringProperty(SET_BOUNDS_KEY));
            frameWidthLabel.textProperty().bind(util.getStringProperty(FRAME_WIDTH_KEY));
            frameHeightLabel.textProperty().bind(util.getStringProperty(FRAME_HEIGHT_KEY));
            colorMask.textProperty().bind(util.getStringProperty(COLOR_MASK_KEY));
            widgetsController.bindButtonText(util.getStringProperty(NEW_WIDGET_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty() && !imagePath.isEmpty();
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.DRAWABLE_GAME_ELEMENT);
        addToData(WizardDataType.NAME, name.getText());
        addToData(WizardDataType.IMAGE, imagePath);
        addToData(WizardDataType.FRAME_X, "" + (int) frameWidth.getValue());
        addToData(WizardDataType.FRAME_Y, "" + (int) frameHeight.getValue());
        addToData(WizardDataType.COLS, Integer.toString(animationGrid.getNumRows()));
        addToData(WizardDataType.COLOR_MASK, colorMaskPath);
        storeContainerData(stringAttributesController);
        storeContainerData(numberAttributesController);
        storeContainerData(triggersController);
        storeContainerData(widgetsController);
        storeContainerData(animationsController);
    }

    public void attachStringAttributes (List<String> stringAttributes) {
        myGlobalStringAttributes = stringAttributes;
    }

    public void attachNumberAttributes (List<String> numberAttributes) {
        myGlobalNumberAttributes = numberAttributes;
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        name.setText(oldValues.getValueByKey(WizardDataType.NAME));
        imagePath = oldValues.getValueByKey(WizardDataType.IMAGE);
        colorMaskPath = oldValues.getValueByKey(WizardDataType.COLOR_MASK);
        try {
            if (!imagePath.isEmpty()) renderImage(new File(imagePath));
            if (!colorMaskPath.isEmpty()) addImageToView(new File(colorMaskPath));
            
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        frameWidth.setValue(Double.parseDouble(oldValues.getValueByKey(WizardDataType.FRAME_X)));
        frameHeight.setValue(Double.parseDouble(oldValues.getValueByKey(WizardDataType.FRAME_Y)));        
        addContainerData(stringAttributesController, oldValues, WizardType.STRING_ATTRIBUTE);
        addContainerData(numberAttributesController, oldValues, WizardType.NUMBER_ATTRIBUTE);
        addContainerData(triggersController, oldValues, WizardType.TRIGGER);
        addContainerData(widgetsController, oldValues, WizardType.WIDGET);
        addContainerData(animationsController, oldValues, WizardType.ANIMATION_SEQUENCE);
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing, use the attachString and Number Attributes instead
    }

    private void launchBoundsWizard () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.BOUNDS_WIZARD, BOUNDS_WIZARD_SIZE);
        if (boundsAlreadyExist()) wiz.launchForEdit(getBoundsFromWizardData());

        Consumer<WizardData> bc = (data) -> {
            if (boundsAlreadyExist()) getWizardData().removeWizardData(getBoundsFromWizardData());
            addWizardData(data);
            wiz.closeStage();
        };
        wiz.setSubmit(bc);
    }

    private boolean boundsAlreadyExist () {
        return !getWizardData().getWizardDataByType(WizardType.BOUNDS).isEmpty();
    }

    private WizardData getBoundsFromWizardData () {
        return getWizardData().getWizardDataByType(WizardType.BOUNDS).get(0);
    }

    /**
     * Gets an image from a file of the user's choice
     * 
     * @return image
     *         an Image of the user's choosing
     * @throws FileNotFoundException
     */
    private File fetchImage () throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                                                 new FileChooser.ExtensionFilter("JPG", ".jpg"));
        fileChooser.setInitialDirectory(new File("resources"));
        File file = fileChooser.showOpenDialog(new Stage());
        return file;
    }

    private void loadColorMask () {
        try {
            File colorMaskFile = fetchImage();
            colorMaskPath = colorMaskFile.getPath();
            addImageToView(colorMaskFile);
        }
        catch (FileNotFoundException | NullPointerException e) {
            displayErrorMessage(UNABLE_TO_LOAD);
        }
    }

    private void loadImage () {
        try {
            spritesheet.setOnMouseClicked(imageScroll.getOnMouseClicked());
            spritesheet.getChildren().clear();
            File imageFile = fetchImage();
            imagePath = imageFile.getPath();
            renderImage(imageFile);
        }
        catch (FileNotFoundException | NullPointerException e) {
            displayErrorMessage(UNABLE_TO_LOAD);
        }
    }

    private void renderImage (File imageFile) throws FileNotFoundException {
        Image image = addImageToView(imageFile);
        initializeSliders(image);
        animationGrid =
                new AnimationGrid(image.getWidth(), image.getHeight(), frameWidth.getValue(),
                                  frameHeight.getValue());
        spritesheet.getChildren().add(animationGrid);
        spritesheet.toFront();
    }

    private Image addImageToView (File imageFile) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(imageFile));
        imageView = new ImageView(image);
        spritesheet.getChildren().add(imageView);
        return image;
    }

    private void initializeSliders (Image image) {
        createSliderListeners();
        createTextFieldListeners();
        frameWidth.setMin(DEFAULT_GRID_MIN);
        frameWidth.setValue(DEFAULT_GRID_VALUE);
        frameWidth.setMax(image.getWidth());
        frameHeight.setMin(DEFAULT_GRID_MIN);
        frameHeight.setValue(DEFAULT_GRID_VALUE);
        frameHeight.setMax(image.getHeight());
    }

    private void initContainers () {
        stringAttributesController.init(GUIPanePath.STRING_ATTRIBUTE_WIZARD,
                                        myGlobalStringAttributes, getAttributeConsumer());
        numberAttributesController.init(GUIPanePath.NUMBER_ATTRIBUTE_WIZARD,
                                        myGlobalNumberAttributes, getAttributeConsumer());
        triggersController.init(GUIPanePath.ACTION_WIZARD, myGlobalNumberAttributes,
                                getActionConsumer(), ACTION_WIZARD_SIZE);
        widgetsController.init(GUIPanePath.WIDGET_WIZARD, myGlobalNumberAttributes,
                               getWidgetConsumer());
        animationsController.setValidToLaunch(isAnimationLaunchValid());
        animationsController.init(GUIPanePath.ANIMATION_WIZARD, imageValues,
                                  getAnimationConsumer(), ANIMATION_WIZARD_SIZE);
    }

    private BiConsumer<Button, WizardData> getActionConsumer () {
        return (button, data) -> button.setText(data.getValueByKey(WizardDataType.ACTION));
    }

    private BiConsumer<Button, WizardData> getAttributeConsumer () {
        return (button, data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ATTRIBUTE) + ": " +
                                data.getValueByKey(WizardDataType.VALUE);
            button.setText(buttonName);
        };
    }

    private BiConsumer<Button, WizardData> getWidgetConsumer () {
        return (button, data) -> button.setText(data.getValueByKey(WizardDataType.WIDGET_TYPE));
    }

    private BiConsumer<Button, WizardData> getAnimationConsumer () {
        return (button, data) -> button.setText(data.getValueByKey(WizardDataType.ANIMATION_TAG));
    }

    private BooleanSupplier isAnimationLaunchValid () {
        return () -> {
            if (imageView != null) {
                List<String> imageValues = new ArrayList<>();
                imageValues.add(imagePath);
                imageValues.add(Double.toString(frameWidth.getValue()));
                imageValues.add(Double.toString(frameHeight.getValue()));
                imageValues.add(colorMaskPath);
                return true;
            }
            else {
                displayErrorMessage(UNABLE_TO_LAUNCH_ANIM);
                return false;
            }
        };
    }

    private void createTextFieldListeners () {
        setTextListener(frameWidthText, (value) -> frameWidth.setValue(value));
        setTextListener(frameHeightText, (value) -> frameHeight.setValue(value));
    }

    private void setTextListener (TextField field, Consumer<Double> cons) {
        field.textProperty().addListener(e -> {
            if (isNumber(field.getText())) {
                cons.accept(Double.parseDouble(field.getText()));
            }
        });
    }

    private void createSliderListeners () {
        setValueListener(frameWidth, (text) -> frameWidthText.setText(text),
                         (gridValue) -> animationGrid.changeSize(gridValue,
                                                                 animationGrid.getFrameY()));
        setValueListener(frameHeight, (text) -> frameHeightText.setText(text),
                         (gridValue) -> animationGrid.changeSize(animationGrid.getFrameX(),
                                                                 gridValue));
    }

    private void setValueListener (Slider slider, Consumer<String> cons,
                                   Consumer<Double> gridCons) {
        slider.valueProperty().addListener(e -> {
            cons.accept("" + (int) slider.getValue());
            if (animationGrid != null) gridCons.accept(slider.getValue());
        });
    }

    private void storeContainerData (CRUDContainer container) {
        container.getWizardDatas().forEach(data -> addWizardData(data));
    }
    
    private void addContainerData (CRUDContainer container, WizardData oldValues, WizardType type) {
        container.addAndRenderWizardDatas(oldValues.getWizardDataByType(type));
    }
    
}
