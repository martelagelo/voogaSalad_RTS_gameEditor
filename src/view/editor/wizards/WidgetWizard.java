package view.editor.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import model.state.gameelement.StateTags;
import model.state.gameelement.traits.AttributeDisplayerTags;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;


/**
 * This represents the Wizard responsible for storing a string attribute along with its attribute
 * name as a key. It only requires both fields to not be null.
 * 
 * @author Joshua, Nishad
 *
 */
public class WidgetWizard extends Wizard {

    private static final int NUMERIC_TEXT_FIELD_VALUE = 2;
    private final static String WIDGET_TYPE_KEY = "WidgetType";
    private final static String ATTRIBUTE_KEY = "Attribute";

    @FXML
    protected ComboBox<String> displayerTag;
    @FXML
    protected ComboBox<String> attribute;
    @FXML
    protected VBox arguments;

    private List<TextField> argFields;

    private List<String> myGlobalNumberAttributes;
    private Set<String> allAttributes;

    @Override
    public boolean checkCanSave () {
        return displayerTag.getSelectionModel().selectedItemProperty().isNotNull().get() &&
               attribute.getSelectionModel().selectedItemProperty().isNotNull().get() &&
               argumentsValid();
    }

    private boolean argumentsValid () {
        for (TextField field : argFields) {
            if (field.textProperty().isNull().get() ||
                argFields.size() == NUMERIC_TEXT_FIELD_VALUE && !isNumber(field.getText())) { return false; }
        }
        return true;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.WIDGET);
        addToData(WizardDataType.ATTRIBUTE, attribute.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.WIDGET_TYPE, displayerTag.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.WIDGET_PARAMETERS, getArguments());
    }

    private String getArguments () {
        StringBuilder sb = new StringBuilder();
        for (TextField field : argFields) {
            sb.append(field.getText().trim() + ",");
        }
        return sb.toString();
    }

    @Override
    public void initialize () {
        super.initialize();
        displayerTag.setItems(
                              FXCollections.observableList(Arrays.asList(AttributeDisplayerTags.values()).stream()
                                                           .map(tag -> tag.name()).collect(Collectors.toList())));
        allAttributes = StateTags.getAllAttributes();
        attribute.setItems(FXCollections.observableList(new ArrayList<>(allAttributes)));
        attribute.valueProperty().addListener( (o, oldVal, newVal) -> newAttribute(newVal));
        myGlobalNumberAttributes = new ArrayList<>();
        argFields = new ArrayList<>();
    }

    private void newAttribute (String newVal) {
        arguments.getChildren().clear();
        if (myGlobalNumberAttributes.contains(newVal) ||
            StateTags.getAllNumericalAttributes().contains(newVal)) {
            TextField field1 = new TextField();
            field1.setPromptText("Min Value");
            arguments.getChildren().add(field1);
            TextField field2 = new TextField();
            field2.setPromptText("Max Value");
            arguments.getChildren().add(field2);
        }
        else {
            TextField field = new TextField();
            field.setPromptText("Label");
            arguments.getChildren().add(field);
        }
    }

    @Override
    protected void attachTextProperties () {
        super.attachTextProperties();
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            displayerTag.promptTextProperty().bind(util.getStringProperty(WIDGET_TYPE_KEY));
            attribute.promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_KEY));
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        displayerTag.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.WIDGET_TYPE));
        attribute.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.ATTRIBUTE));        
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        myGlobalNumberAttributes = values;
    }
}
