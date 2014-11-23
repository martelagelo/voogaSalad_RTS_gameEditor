package editor.wizards;

import gamemodel.GameElementStateFactory;
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
        setDataName(GameElementStateFactory.TRIGGER);
        addToData(GameElementStateFactory.CONDITION, condition.getText());
        addToData(GameElementStateFactory.ACTION, condition.getText());
    }

}
