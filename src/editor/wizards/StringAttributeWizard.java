package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * This represents the Wizard responsible for storing a string attribute along with its attribute
 * name as a key. It only requires both fields to not be null.
 * 
 * @author Joshua, Nishad
 *
 */
public class StringAttributeWizard extends Wizard {
    @FXML
    private TextField key;
    @FXML
    private TextField stringValue;

    @Override
    public boolean checkCanSave () {
        return !key.getText().isEmpty() && !stringValue.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.STRING_ATTRIBUTE);
        addToData(WizardDataType.ATTRIBUTE, key.getText());
        addToData(WizardDataType.VALUE, stringValue.getText());
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        key.setText(oldValues.getValueByKey(WizardDataType.ATTRIBUTE));
        stringValue.setText(oldValues.getValueByKey(WizardDataType.VALUE));
    }    
}
