package view.editor.wizards;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class SelectableGameElementWizard extends Wizard {

    private final static String NAME_KEY = "Name";
    private final static String NEW_TRIGGER_KEY = "NewTrigger";
    private final static String NEW_STRING_ATTRIBUTE_KEY = "NewStringAttribute";
    private final static String NEW_NUMBER_ATTRIBUTE_KEY = "NewNumberAttribute";
    private final static String LOAD_IMAGE_KEY = "LoadImage";

    @FXML
    private ScrollPane leftPane;
    @FXML
    private TextField name;
    @FXML
    private Button trigger;
    @FXML
    private VBox existingTriggers;

    @FXML
    private Button stringAttribute;
    @FXML
    private VBox existingStringAttributes;

    @FXML
    private Button numberAttribute;
    @FXML
    private VBox existingNumberAttributes;

    @FXML
    private Button image;
    @FXML
    private Group spritesheet;
    @FXML
    private ScrollPane imageScroll;
    @FXML
    private Button animation;

    private List<String> myGlobalStringAttributes;
    private List<String> myGlobalNumberAttributes;
    private ImageView imageView;
    private String imagePath;
    private String jsonPath;

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchTriggerEditor () {
        launchNestedWizard(GUIPanePath.TRIGGER_WIZARD, existingTriggers, new ArrayList<String>());
    }

    /**
     * Launches a String Attribute Wizard
     * 
     */
    private void launchStringAttributeEditor () {
        launchNestedWizard(GUIPanePath.STRING_ATTRIBUTE_WIZARD, existingStringAttributes,
                           myGlobalStringAttributes);
    }

    /**
     * Launches a Number Attribute Wizard
     * 
     */
    private void launchNumberAttributeEditor () {
        launchNestedWizard(GUIPanePath.NUMBER_ATTRIBUTE_WIZARD, existingNumberAttributes,
                           myGlobalNumberAttributes);
    }

    /**
     * Launches an Animation Wizard
     * 
     */
    private void launchAnimationEditor () {

    }

    private void launchNestedWizard (GUIPanePath path, VBox existing, List<String> globalAttrs) {
        Wizard wiz = WizardUtility.loadWizard(path, new Dimension(300, 300));
        for (String atr : globalAttrs) {
            System.out.println(atr);
        }
        wiz.loadGlobalValues(globalAttrs);
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);

            Button edit = new Button();
            edit.setText((new ArrayList<String>(data.getData().values())).get(0));
            edit.setOnAction(e -> launchEditWizard(path, data, edit, globalAttrs));
            Button delete = new Button();
            delete.setText("X");
            HBox newElement = new HBox(edit, delete);
            delete.setOnAction(e -> {
                removeWizardData(data);
                existing.getChildren().remove(newElement);
            });
            existing.getChildren().add(newElement);

            wiz.getStage().close();
        };
        wiz.setSubmit(bc);
    }

    private void launchEditWizard (GUIPanePath path,
                                   WizardData oldData,
                                   Button button,
                                   List<String> globalAttrs) {
        Wizard wiz = WizardUtility.loadWizard(path, new Dimension(300, 300));
        wiz.launchForEdit(oldData);
        Consumer<WizardData> bc = (data) -> {
            removeWizardData(oldData);
            addWizardData(data);
            oldData.clear();
            for (WizardDataType type : data.getData().keySet()) {
                oldData.addDataPair(type, data.getValueByKey(type));
            }
            button.setText((new ArrayList<String>(data.getData().values())).get(0));
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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                                                 new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            spritesheet.getChildren().clear();
            Image image = new Image(new FileInputStream(file));
            imagePath = file.getPath();
            imageView = new ImageView(image);
            spritesheet.getChildren().add(imageView);
        }
        catch (Exception e) {
            displayErrorMessage("Unable to Load Image");
        }
    }

    private void loadAnimationJSON () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialDirectory(new File("resources/gameelementresources"));
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            jsonPath = file.getPath();
        }
        catch (Exception e) {
            displayErrorMessage("Unable to Load JSON File");
        }
    }

    /**
     * Binds all the listeners to the scene
     * 
     */
    @Override
    public void initialize () {
        super.initialize();
        leftPane.setFitToWidth(true);
        root.setDividerPositions(0.4);
        trigger.setOnAction(e -> launchTriggerEditor());
        stringAttribute.setOnAction(e -> launchStringAttributeEditor());
        numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
        animation.setOnAction(e -> launchAnimationEditor());
        image.setOnAction(i -> loadImage());
        animation.setOnAction(i -> loadAnimationJSON());
        imagePath = "";
        jsonPath = "";
        attachTextProperties();
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
            trigger.textProperty().bind(util.getStringProperty(NEW_TRIGGER_KEY));
            stringAttribute.textProperty().bind(util.getStringProperty(NEW_STRING_ATTRIBUTE_KEY));
            numberAttribute.textProperty().bind(util.getStringProperty(NEW_NUMBER_ATTRIBUTE_KEY));
            image.textProperty().bind(util.getStringProperty(LOAD_IMAGE_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty() && imageView != null;
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.DRAWABLE_GAME_ELEMENT);
        addToData(WizardDataType.NAME, name.getText());
        addToData(WizardDataType.IMAGE, imagePath);
        addToData(WizardDataType.ANIMATOR_STATE, jsonPath);
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
