package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.state.gameelement.StateTags;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import engine.gameRepresentation.renderedRepresentation.attributeDisplayer.AttributeDisplayerTags.AttributeDisplayerType;


/**
 * This represents the Wizard responsible for storing a string attribute along with its attribute
 * name as a key. It only requires both fields to not be null.
 * 
 * @author Joshua, Nishad
 *
 */
public class WidgetWizard extends Wizard {

    private final static String WIDGET_TYPE_KEY = "WidgetType";
    private final static String ATTRIBUTE_KEY = "Attribute";
    private final static String MIN_VALUE_KEY = "MinValue";
    private final static String MAX_VALUE_KEY = "MaxValue";
    private final static String LABEL_KEY = "Label";

    @FXML
    protected ComboBox<AttributeDisplayerType> displayerTag;
    @FXML
    protected ComboBox<String> attribute;
    @FXML
    protected VBox arguments;

    private List<String> myGlobalStringAttributes;
    private List<String> myGlobalNumberAttributes;
    private Set<String> allAttributes;

    @Override
    public boolean checkCanSave () {
        return true;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.WIDGET);

    }

    @Override
    public void initialize () {
        super.initialize();
        displayerTag.setItems(FXCollections.observableArrayList(AttributeDisplayerType.values()));
        allAttributes = StateTags.getAllAttributes();
        attribute.setItems(FXCollections.observableList(new ArrayList<>(allAttributes)));
        attribute.valueProperty().addListener( (o, oldVal, newVal) -> newAttribute(newVal));
        myGlobalNumberAttributes = new ArrayList<>();
        myGlobalStringAttributes = new ArrayList<>();
    }

    private void newAttribute (String newVal) {
        arguments.getChildren().clear();
        if (myGlobalNumberAttributes.contains(newVal) ||
            StateTags.getAllNumericalAttributes().contains(newVal)) {
            arguments.getChildren().add(new TextField());
        }
        arguments.getChildren().add(new TextField());
    }

    @Override
    protected void attachTextProperties () {
        super.attachTextProperties();
         MultiLanguageUtility util = MultiLanguageUtility.getInstance();
         try {
         .promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_KEY_KEY));
         value.promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_VALUE_KEY));
         }
         catch (LanguageException e) {
         displayErrorMessage(e.getMessage());
         }
    }

    public void attachStringAttributes (List<String> stringAttributes) {
        myGlobalStringAttributes = stringAttributes;
    }

    public void attachNumberAttributes (List<String> numberAttributes) {
        myGlobalNumberAttributes = numberAttributes;
    }

    @Override
    public void launchForEdit (WizardData oldValues) {

    }

    @Override
    public void loadGlobalValues (List<String> values) {

    }
}
