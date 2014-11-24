package editor.wizards;

import gamemodel.GameElementStateFactory;
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
        setDataName(GameElementStateFactory.NUMBER_ATTRIBUTE);
        addToData(GameElementStateFactory.ATTRIBUTE, key.getText());
        addToData(GameElementStateFactory.VALUE, numberValue.getText());
    }
}
