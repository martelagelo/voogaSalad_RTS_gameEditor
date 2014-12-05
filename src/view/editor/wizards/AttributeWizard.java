package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * This represents the Wizard responsible for storing a string attribute along with its attribute
 * name as a key. It only requires both fields to not be null.
 * 
 * @author Joshua, Nishad
 *
 */
public class AttributeWizard extends Wizard {
    @FXML
    protected ComboBox<String> key;
    @FXML
    protected TextField value;

    protected ObservableList<String> attributes;
    
    private final String ATTRIBUTE_KEY_KEY = "AttributeKey";
    private final String ATTRIBUTE_VALUE_KEY = "AttributeValue";

    @Override
    public boolean checkCanSave () {
        return areFieldsNotNull();
    }

    protected boolean areFieldsNotNull () {
        return (key.getSelectionModel().getSelectedItem() != null ||
               key.valueProperty().get() != null) && !value.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.STRING_ATTRIBUTE);
        storeKeyValuePair();
    }

    protected void storeKeyValuePair () {
        addToData(WizardDataType.ATTRIBUTE, key.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.VALUE, value.getText());
    }
    
    @Override
    public void initialize () {
        super.initialize();
        attributes = FXCollections.observableList(new ArrayList<>());
        key.setItems(attributes);
    }
    

    @Override
    protected void attachTextProperties () {
        super.attachTextProperties();
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            key.promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_KEY_KEY));
            value.promptTextProperty().bind(util.getStringProperty(ATTRIBUTE_VALUE_KEY));
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        key.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.ATTRIBUTE));
        value.setText(oldValues.getValueByKey(WizardDataType.VALUE));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        attributes.addAll(values);
    }
}
