package editor.wizards;

import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
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
        setDataType(WizardDataType.CAMPAIGN);
        addToData(WizardDataType.NAME, name.getText());
    }

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            name.textProperty().bind(util.getStringProperty(NEW_CAMPAIGN_DEFAULT_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            // TODO Show this exception
        }
    }
}
