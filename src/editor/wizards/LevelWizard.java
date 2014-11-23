package editor.wizards;

import gamemodel.GameElementStateFactory;
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
        setDataName(GameElementStateFactory.LEVEL);
        addToData(GameElementStateFactory.CAMPAIGN, campaignName.getText());
        addToData(GameElementStateFactory.NAME, levelName.getText());
    }
}
