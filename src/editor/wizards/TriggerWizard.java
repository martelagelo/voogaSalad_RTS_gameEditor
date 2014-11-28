package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class TriggerWizard extends Wizard {

    @FXML
    private TextField actionType;
    @FXML
    private TextField action;

    @Override
    public boolean checkCanSave () {
        return !actionType.getText().isEmpty() && !action.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.TRIGGER);
        addToData(WizardDataType.ACTIONTYPE, actionType.getText());
        addToData(WizardDataType.ACTION, action.getText());
    }

}
