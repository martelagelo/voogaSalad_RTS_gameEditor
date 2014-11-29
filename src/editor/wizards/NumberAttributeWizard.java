package editor.wizards;

import java.util.regex.Pattern;
import javafx.fxml.FXML;
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
