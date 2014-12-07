package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
public class DrawableGameElementWizard extends Wizard {

    private static final String UNABLE_TO_LAUNCH_ANIM = "Can't launch due to unspecified image information";
    private static final String UNABLE_TO_LOAD = "Unable to Load Image";
    private static final String DELETE_TEXT = "X";
    private static final Dimension BOUNDS_WIZARD_SIZE = new Dimension(500, 700);
    private static final Dimension ACTION_WIZARD_SIZE = new Dimension(400, 600);
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

    private final int DEFAULT_GRID_MIN = 10;
    private final int DEFAULT_GRID_VALUE = 100;
    private final int ANIMATION_WIZARD_WIDTH = 800;
    private final int ANIMATION_WIZARD_HEIGHT = 800;
    private final int ATTRIBUTE_WIZARD_WIDTH = 300;
    private final int ATTRIBUTE_WIZARD_HEIGHT = 300;

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
    private VBox existingWidgets;
    @FXML
    private Button setBounds;
    @FXML
    private Button widget;

    private List<String> myGlobalStringAttributes;
    private List<String> myGlobalNumberAttributes;
    private ImageView imageView;
    private AnimationGrid animationGrid;
    private String imagePath;
    private String colorMaskPath;
    
    private void launchActionWizard () {
        launchNestedWizard(GUIPanePath.ACTION_WIZARD, existingTriggers, myGlobalNumberAttributes,
                           getActionConsumer(), ACTION_WIZARD_SIZE);
    }

    private BiConsumer<Button, WizardData> getActionConsumer () {
        BiConsumer<Button, WizardData> consumer = (Button button, WizardData data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ACTION);
            button.setText(buttonName);
        };
        return consumer;
    }

    private void launchStringAttributeWizard () {
        launchNestedWizard(GUIPanePath.STRING_ATTRIBUTE_WIZARD, existingStringAttributes,
                           myGlobalStringAttributes, 
                           getAttributeConsumer(), new Dimension(
                        		   ATTRIBUTE_WIZARD_WIDTH, 
                        		   ATTRIBUTE_WIZARD_HEIGHT
                        		   ));
    }

    private BiConsumer<Button, WizardData> getAttributeConsumer () {
        BiConsumer<Button, WizardData> consumer = (Button button, WizardData data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ATTRIBUTE) + ": " +
                    data.getValueByKey(WizardDataType.VALUE);
            button.setText(buttonName);
        };
        return consumer;
    }

    private void launchNumberAttributeWizard () {
        launchNestedWizard(GUIPanePath.NUMBER_ATTRIBUTE_WIZARD, existingNumberAttributes,
                           myGlobalNumberAttributes, getAttributeConsumer(), new Dimension(
                        		   ATTRIBUTE_WIZARD_WIDTH, 
                        		   ATTRIBUTE_WIZARD_HEIGHT
                        		   ));
    }
    
    private void launchWidgetWizard () {
        launchNestedWizard(GUIPanePath.WIDGET_WIZARD, existingWidgets,
    			myGlobalNumberAttributes, getWidgetConsumer(), new Dimension(
             		   ATTRIBUTE_WIZARD_WIDTH, 
             		   ATTRIBUTE_WIZARD_HEIGHT
             		   ));
    }

    private BiConsumer<Button, WizardData> getWidgetConsumer () {
        // TODO Auto-generated method stub
        return null;
    }

    private void launchAnimationWizard () {
        if (imageView != null) {
            List<String> imageValues = new ArrayList<>();            
            imageValues.add(imagePath);            
            imageValues.add(Double.toString(frameWidth.getValue()));
            imageValues.add(Double.toString(frameHeight.getValue()));
            imageValues.add(colorMaskPath);
            launchNestedWizard(GUIPanePath.ANIMATION_WIZARD, existingAnimations,
                               imageValues, getAnimationConsumer(), new Dimension(ANIMATION_WIZARD_WIDTH, ANIMATION_WIZARD_HEIGHT));
        }
        else {
            displayErrorMessage(UNABLE_TO_LAUNCH_ANIM);
        }
    }

    private BiConsumer<Button, WizardData> getAnimationConsumer () {
        BiConsumer<Button, WizardData> consumer = (Button button, WizardData data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ANIMATION_TAG);
            button.setText(buttonName);
        };
        return consumer;
    }

    private void launchBoundsWizard () {
        Wizard wiz = WizardUtility.loadWizard(GUIPanePath.BOUNDS_WIZARD, BOUNDS_WIZARD_SIZE);
        if (!getWizardData().getWizardDataByType(WizardType.BOUNDS).isEmpty()) {
            wiz.launchForEdit(getWizardData().getWizardDataByType(WizardType.BOUNDS).get(0));
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
                                     BiConsumer<Button, WizardData> setTextConsumer,
                                     Dimension dim) {
        Wizard wiz = WizardUtility.loadWizard(path, dim);
        wiz.loadGlobalValues(globalAttrs);
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);
            HBox newElement = new HBox();
            Button edit = new Button();
                setTextConsumer.accept(edit, data);                               
                edit.setOnAction(e -> launchEditWizard(path, data, edit, setTextConsumer,
                                                       globalAttrs, dim));
                edit.setMaxWidth(Double.MAX_VALUE);
                newElement.getChildren().add(edit);

                Button delete = new Button();
                delete.setText(DELETE_TEXT);
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
                                   BiConsumer<Button, WizardData> setTextConsumer, 
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
            setTextConsumer.accept(button, data);
            wiz.closeStage();
        };
        wiz.setSubmit(bc);
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
            Image image = new Image(new FileInputStream(colorMaskFile));
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);
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
            Image image = new Image(new FileInputStream(imageFile));            
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);
            initializeSliders(image);
            animationGrid =
                    new AnimationGrid(image.getWidth(), image.getHeight(), frameWidth.getValue(),
                                      frameHeight.getValue());
            spritesheet.getChildren().add(animationGrid);
            spritesheet.toFront();
        }
        catch (FileNotFoundException | NullPointerException e) {
            displayErrorMessage(UNABLE_TO_LOAD);
        }
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

    @Override
    public void initialize () {
        super.initialize();
        trigger.setOnAction(e -> launchActionWizard());
        stringAttribute.setOnAction(e -> launchStringAttributeWizard());
        numberAttribute.setOnAction(e -> launchNumberAttributeWizard());
        // widget.setOnAction(e -> launchWidgetWizard());
        animation.setOnAction(e -> launchAnimationWizard());
        setBounds.setOnAction(e -> launchBoundsWizard());
        image.setOnAction(i -> loadImage());
        colorMask.setOnAction(i -> loadColorMask());
        imagePath = "";        
        colorMaskPath = "";
        errorMessage.setFill(Paint.valueOf("white"));                
    }

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
            frameWidthLabel.textProperty().bind(util.getStringProperty(FRAME_WIDTH_KEY));
            frameHeightLabel.textProperty().bind(util.getStringProperty(FRAME_HEIGHT_KEY));
            colorMask.textProperty().bind(util.getStringProperty(COLOR_MASK_KEY));
            widget.textProperty().bind(util.getStringProperty(NEW_WIDGET_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            DialogBoxUtility.createMessageDialog(e.getMessage());
        }
    }

    private void createTextFieldListeners () {
        setTextListener(frameWidthText, (value) -> frameWidth.setValue(value));
        setTextListener(frameHeightText, (value) -> frameHeight.setValue(value));
    }
    
    private void setTextListener(TextField field, Consumer<Double> cons) {
        field.textProperty().addListener(e -> {
            if (isNumber(field.getText())) {
                cons.accept(Double.parseDouble(field.getText()));
            }
        });
    }

    private void createSliderListeners () {
        setValueListener(frameWidth, (text) -> frameWidthText.setText(text),
                         (gridValue) -> animationGrid.changeSize(gridValue, animationGrid.getFrameY()));
        setValueListener(frameHeight, (text) -> frameHeightText.setText(text),
                         (gridValue) -> animationGrid.changeSize(animationGrid.getFrameX(), gridValue));        
    }
    
    private void setValueListener(Slider slider, Consumer<String> cons, 
                                  Consumer<Double> gridCons) {
        slider.valueProperty().addListener(e -> {
            cons.accept("" + (int) slider.getValue());
            if (animationGrid != null) {
                gridCons.accept(slider.getValue());
            }
        });
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
        addToData(WizardDataType.COLS, Integer.toString(animationGrid.getNumColumns()));
        addToData(WizardDataType.COLOR_MASK, colorMaskPath);
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
