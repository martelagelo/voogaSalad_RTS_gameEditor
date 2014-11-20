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
    private TextField condition;
    @FXML
    private TextField action;       
    
    @Override
    public boolean checkCanSave () {
        return !condition.getText().isEmpty() && !action.getText().isEmpty();
    }

    @Override
    public void updateData () {
        addToData("Condition", condition.getText());
        addToData("Action", condition.getText());
    }

}
