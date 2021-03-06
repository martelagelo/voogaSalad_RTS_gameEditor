package view.editor.wizards;

import java.util.List;
import model.data.WizardData;
import model.data.WizardDataType;
import model.data.WizardType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;


/**
 * This is the concrete implementation of a Wizard used to create a new CampaignState within our
 * GameState. It is spawned within the file menu and only holds a singular textfield with the name
 * of the new campaign.
 * 
 * @author Joshua, Nishad
 *
 */
public class CampaignWizard extends Wizard {

    private final static String NEW_CAMPAIGN_DEFAULT_KEY = "NewCampaignDefault";
    @FXML
    private TextField name;

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.CAMPAIGN);
        addToData(WizardDataType.NAME, name.getText());
    }

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            name.promptTextProperty().bind(util.getStringProperty(NEW_CAMPAIGN_DEFAULT_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        name.setText(oldValues.getValueByKey(WizardDataType.NAME));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        //do nothing
    }
}
