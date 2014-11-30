package editor.wizards;

import java.util.ArrayList;
import java.util.List;
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
        setDataType(WizardDataType.STRING_ATTRIBUTE);
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
    public void launchForEdit (WizardData oldValues) {
        key.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.ATTRIBUTE));
        value.setText(oldValues.getValueByKey(WizardDataType.VALUE));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        attributes.addAll(values);
    }
}
