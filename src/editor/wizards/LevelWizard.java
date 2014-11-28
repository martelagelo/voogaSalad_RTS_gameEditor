package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * 
 * @author Nishad
 *
 */
public class LevelWizard extends Wizard {
    @FXML
    private TextField campaignName;
    @FXML
    private TextField levelName;

    @Override
    public boolean checkCanSave () {
        return !campaignName.getText().isEmpty() && !levelName.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.LEVEL);
        addToData(WizardDataType.CAMPAIGN, campaignName.getText());
        addToData(WizardDataType.NAME, levelName.getText());
    }
}
