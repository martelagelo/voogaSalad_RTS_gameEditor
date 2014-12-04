package view.editor.wizards;

import java.util.regex.Pattern;


/**
 * This represents the Wizard used to accept a numerical value with an attribute name. It checks
 * whether the inputted value is numeric, and then stores this value as a string, to conform with
 * the WizardData data structure.
 * 
 * @author Joshua, Nishad
 *
 */
public class NumberAttributeWizard extends AttributeWizard {    

    @Override
    public boolean checkCanSave () {
        return areFieldsNotNull() &&
               Pattern.matches(NUM_REGEX, value.getText());
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.NUMBER_ATTRIBUTE);
        storeKeyValuePair();
    }

}
