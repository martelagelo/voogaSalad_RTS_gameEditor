package view.editor.wizards;

import java.util.List;
import java.util.regex.Pattern;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * Wizard for setting position of a GameElementState to add to a level
 * 
 * @author Nishad
 *
 */
public class PositionWizard extends Wizard {

    @FXML
    protected TextField xValue;
    @FXML
    protected TextField yValue;
    
    private final String X_VALUE_KEY = "XValue";
    private final String Y_VALUE_KEY = "YValue";

    @Override
    public boolean checkCanSave () {
        return !xValue.getText().isEmpty() && Pattern.matches(NUM_REGEX, xValue.getText()) &&
               !yValue.getText().isEmpty() && Pattern.matches(NUM_REGEX, yValue.getText());
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.POSITION);
        addToData(WizardDataType.X_POSITION, xValue.getText());
        addToData(WizardDataType.Y_POSITION, yValue.getText());
    }
    
    @Override
    protected void attachTextProperties () {
        super.attachTextProperties();
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            xValue.promptTextProperty().bind(util.getStringProperty(X_VALUE_KEY));
            yValue.promptTextProperty().bind(util.getStringProperty(Y_VALUE_KEY));
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }


    @Override
    public void launchForEdit (WizardData oldValues) {
        xValue.setText(oldValues.getValueByKey(WizardDataType.X_POSITION));
        yValue.setText(oldValues.getValueByKey(WizardDataType.Y_POSITION));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing
    }
}
