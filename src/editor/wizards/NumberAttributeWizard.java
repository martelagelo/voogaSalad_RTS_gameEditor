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
        System.out.println(numberValue.getText());
        
        return !key.getText().isEmpty() && !numberValue.getText().isEmpty() &&
               Pattern.matches("-?[0-9]+\\.?[0-9]*", numberValue.getText());
    }

    @Override
    public void updateData () {
        addToData("NumAttribute", key.getText());
        addToData("Value", numberValue.getText());
    }
}
