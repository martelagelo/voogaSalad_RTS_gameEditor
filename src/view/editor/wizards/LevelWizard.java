package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import util.multilanguage.LanguageException;
import util.multilanguage.MultiLanguageUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * This is the concrete implementation of a Wizard used to create a new LevelState within our
 * GameState. It is spawned within the file menu and holds two textfields for campaignName and
 * campaignLevel. It is important to note that the Wizard does not hold the responsibility for
 * checking whether or not the campaign actually exists because it has no knowledge of the
 * GameState. Therefore, it will send this to the class which launches the wizard which should hold
 * the checking knowledge.
 * 
 * @author Nishad
 *
 */
public class LevelWizard extends Wizard {

    private final String NEW_LEVEL_DEFAULT_KEY = "NewLevelDefault";
    private final String NEW_CAMPAIGN_DEFAULT_KEY = "NewCampaignDefault";
    @FXML
    private ComboBox<String> campaignName;
    @FXML
    private TextField levelName;
    
    private ObservableList<String> campaigns;

    @Override
    public boolean checkCanSave () {
        return campaignName.getSelectionModel().getSelectedItem() != null &&
               !levelName.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.LEVEL);
        addToData(WizardDataType.CAMPAIGN, campaignName.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.NAME, levelName.getText());
    }

    @Override
    protected void attachTextProperties () {
        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        try {
            levelName.promptTextProperty().bind(util.getStringProperty(NEW_LEVEL_DEFAULT_KEY));
            campaignName.promptTextProperty().bind(util.getStringProperty(NEW_CAMPAIGN_DEFAULT_KEY));
            super.attachTextProperties();
        }
        catch (LanguageException e) {
            // TODO Do something with this exception
        }
    }
    
    @Override
    public void initialize () {
        super.initialize();
        campaigns = FXCollections.observableList(new ArrayList<>());
        campaignName.setItems(campaigns);
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        campaignName.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.CAMPAIGN));
        levelName.setText(oldValues.getValueByKey(WizardDataType.NAME));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        campaigns.addAll(values);
    }
}