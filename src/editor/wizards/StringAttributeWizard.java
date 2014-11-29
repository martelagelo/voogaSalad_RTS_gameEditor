package editor.wizards;

import gamemodel.GameElementStateFactory;
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
        setDataName(GameElementStateFactory.STRING_ATTRIBUTE);
        addToData(GameElementStateFactory.ATTRIBUTE, key.getText());
        addToData(GameElementStateFactory.VALUE, stringValue.getText());
    }
}
