package editor.wizards;

import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * 
 * @author joshua
 *
 */
public class NumberAttributeWizard extends Wizard {
    @FXML
    private TextField key;
    @FXML
    private TextField numberValue;

    @Override
    public boolean checkCanSave () {        
        return !key.getText().isEmpty() && !numberValue.getText().isEmpty() &&
               Pattern.matches("-?[0-9]+\\.?[0-9]*", numberValue.getText());
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.NUMBER_ATTRIBUTE);
        addToData(WizardDataType.ATTRIBUTE, key.getText());
        addToData(WizardDataType.VALUE, numberValue.getText());
    }
}
