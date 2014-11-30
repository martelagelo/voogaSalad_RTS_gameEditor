package editor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * This represents the Wizard used to accept a numerical value with an attribute name. It checks
 * whether the inputted value is numeric, and then stores this value as a string, to conform with
 * the WizardData data structure.
 * 
 * @author Joshua, Nishad
 *
 */
public class NumberAttributeWizard extends Wizard {
    @FXML
    private ComboBox<String> key;
    @FXML
    private TextField numberValue;

    private ObservableList<String> attributes;

    @Override
    public boolean checkCanSave () {
        return (key.getSelectionModel().getSelectedItem() != null ||
               key.valueProperty().get() != null) && !numberValue.getText().isEmpty() &&
               Pattern.matches("-?[0-9]+\\.?[0-9]*", numberValue.getText());
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.NUMBER_ATTRIBUTE);
        addToData(WizardDataType.ATTRIBUTE, key.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.VALUE, numberValue.getText());
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
        numberValue.setText(oldValues.getValueByKey(WizardDataType.VALUE));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        attributes.addAll(values);
    }
}
