package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class CampaignWizard extends Wizard {
    @FXML
    private TextField name;

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.CAMPAIGN);
        addToData(WizardDataType.NAME, name.getText());
    }
}
