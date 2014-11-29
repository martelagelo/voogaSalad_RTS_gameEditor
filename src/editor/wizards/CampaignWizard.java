package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * This is the concrete implementation of a Wizard used to create a new CampaignState within our
 * GameState. It is spawned within the file menu and only holds a singular textfield with the name
 * of the new campaign.
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
