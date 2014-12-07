package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
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

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchActionEditor () {
        launchNestedWizard(GUIPanePath.ACTION_WIZARD, existingTriggers, myGlobalNumberAttributes,
                           getActionConsumer(), new Dimension(400, 600));
    }

    private BiConsumer<Button, WizardData> getActionConsumer () {
        BiConsumer<Button, WizardData> consumer = (Button button, WizardData data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ACTION);
            button.setText(buttonName);
        };
        return consumer;
    }

    /**
     * Launches a String Attribute Wizard
     * 
     */
    private void launchStringAttributeEditor () {
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

    /**
     * Launches a Number Attribute Wizard
     * 
     */
    private void launchNumberAttributeEditor () {
        launchNestedWizard(GUIPanePath.NUMBER_ATTRIBUTE_WIZARD, existingNumberAttributes,
                           myGlobalNumberAttributes, getAttributeConsumer(), new Dimension(
                        		   ATTRIBUTE_WIZARD_WIDTH, 
                        		   ATTRIBUTE_WIZARD_HEIGHT
                        		   ));
    }
    
    /**
     * Launches a Widget Wizard
     *
     */
    private void launchWidgetEditor () {
    	launchNestedWizard(GUIPanePath.WIDGET_WIZARD, existingNumberAttributes,
    			myGlobalNumberAttributes, getAttributeConsumer(), new Dimension(
             		   ATTRIBUTE_WIZARD_WIDTH, 
             		   ATTRIBUTE_WIZARD_HEIGHT
             		   ));
    }

    private void launchAnimationEditor () {
        if (imageView != null) {
            List<String> imageValues = new ArrayList<>();
            imageValues.add(imagePath);
            imageValues.add(colorMaskPath);
            imageValues.add(Double.toString(frameWidth.getValue()));
            imageValues.add(Double.toString(frameHeight.getValue()));
            launchNestedWizard(GUIPanePath.ANIMATION_WIZARD, existingAnimations,
                               imageValues, getAnimationConsumer(), new Dimension(ANIMATION_WIZARD_WIDTH, ANIMATION_WIZARD_HEIGHT));
        }
        else {
            displayErrorMessage("Can't launch due to unspecified image information");
        }
    }

    private BiConsumer<Button, WizardData> getAnimationConsumer () {
        BiConsumer<Button, WizardData> consumer = (Button button, WizardData data) -> {
            String buttonName = data.getValueByKey(WizardDataType.ANIMATION_TAG);
            button.setText(buttonName);
        };
        return consumer;
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

    /**
     * Fired when the user uploads a new color mask
     * 
     */
    private void loadColorMask () {
        try {
            File colorMaskFile = fetchImage();
            colorMaskPath = colorMaskFile.getPath();
            Image image = new Image(new FileInputStream(colorMaskFile));
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);
        }
        catch (FileNotFoundException e) {
            displayErrorMessage("Unable to Load Image");
        }
    }

    /**
     * Fired when the user uploads a new picture
     * 
     * Credit for the buffered image code goes to
     * StackOverflow user mathew11
     */
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
            displayErrorMessage("Unable to Load Image");
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

    /**
     * Binds all the listeners to the scene
     * 
     */
    @Override
    public void initialize () {
        super.initialize();
        trigger.setOnAction(e -> launchActionEditor());
        stringAttribute.setOnAction(e -> launchStringAttributeEditor());
        numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
        widget.setOnAction(e -> launchWidgetEditor());
        animation.setOnAction(e -> launchAnimationEditor());
        setBounds.setOnAction(e -> launchBoundsEditor());
        image.setOnAction(i -> loadImage());
        colorMask.setOnAction(i -> loadColorMask());
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
            frameWidthLabel.textProperty().bind(util.getStringProperty(FRAME_WIDTH_KEY));
            frameHeightLabel.textProperty().bind(util.getStringProperty(FRAME_HEIGHT_KEY));
            colorMask.textProperty().bind(util.getStringProperty(COLOR_MASK_KEY));
            widget.textProperty().bind(util.getStringProperty(NEW_WIDGET_KEY));
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
        return !name.getText().isEmpty() && imageView != null;
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
