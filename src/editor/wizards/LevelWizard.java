package editor.wizards;

import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * 
 * @author Nishad
 *
 */
public class LevelWizard extends Wizard {

    private final String NEW_LEVEL_DEFAULT_KEY = "NewLevelDefault";
    private final String NEW_CAMPAIGN_DEFAULT_KEY = "NewCampaignDefault";
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

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            levelName.textProperty().bind(util.getStringProperty(NEW_LEVEL_DEFAULT_KEY));
            campaignName.textProperty().bind(util.getStringProperty(NEW_CAMPAIGN_DEFAULT_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            // TODO Do something with this exception
        }
    }
}
