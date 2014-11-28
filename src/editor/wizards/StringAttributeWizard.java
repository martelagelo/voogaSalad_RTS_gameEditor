package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
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
}
