package editor.wizards;

import gamemodel.GameElementStateFactory;
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
        setDataName(GameElementStateFactory.CAMPAIGN);
        addToData(GameElementStateFactory.NAME, name.getText());
    }
}
