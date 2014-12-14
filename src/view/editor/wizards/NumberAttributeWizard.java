package view.editor.wizards;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import model.data.WizardType;
import model.state.gameelement.StateTags;


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
        return areFieldsNotNull() && isNumber(value.getText());
    }

    @Override
    public void initialize () {
        super.initialize();
        attributes = StateTags.getAllNumericalAttributes();
        key.setItems(FXCollections.observableList(new ArrayList<>(attributes)));
    }
    @Override
    public void updateData () {
        setWizardType(WizardType.NUMBER_ATTRIBUTE);
        storeKeyValuePair();
    }       

}
