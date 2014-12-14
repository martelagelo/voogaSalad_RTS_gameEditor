package view.editor.wizards;

import java.util.List;
import model.data.WizardData;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;


/**
 * This is the wizard intended for the creation of a Participant object.
 * Participants can be either AI or human.
 * 
 * @author joshua
 *
 */
public class ParticipantWizard extends Wizard {

    @FXML
    private RadioButton AI;
    @FXML
    private TextField name;
    @FXML
    private ColorPicker teamColor;
    @FXML
    private ScrollPane leftPane;

    @Override
    public boolean checkCanSave () {
        return true;
    }

    @Override
    public void updateData () {

    }

    @Override
    public void initialize () {
        super.initialize();

    }

    @Override
    public void launchForEdit (WizardData oldValues) {

    }

    @Override
    public void loadGlobalValues (List<String> values) {

    }
}
