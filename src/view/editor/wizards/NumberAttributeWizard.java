package view.editor.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import model.state.gameelement.StateTags;
import model.state.gameelement.StateTags.StateType;


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
    public void initialize () {
        super.initialize();
        attributes = Arrays.asList(StateTags.values())
                .stream().filter(tag -> tag.getType().equals(StateType.NUMBER))
                .map(tag -> tag.getValue())
                .collect(Collectors.toSet()); 
        key.setItems(FXCollections.observableList(new ArrayList<>(attributes)));
    }
    @Override
    public void updateData () {
        setWizardType(WizardType.NUMBER_ATTRIBUTE);
        storeKeyValuePair();
    }       

}
