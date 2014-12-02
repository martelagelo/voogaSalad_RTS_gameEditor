package view.editor.wizards;

import java.util.List;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AnimationWizard extends Wizard {
    @FXML
    private AnchorPane leftPane;
    
    @FXML
    private VBox animationTagList;
    
    @FXML
    private ComboBox<String> animationTag;
    
    @Override
    public boolean checkCanSave () {
        return !startFrame.getText().isEmpty() &&
        Pattern.matches(NUM_REGEX, startFrame.getText()) && !stopFrame.getText().isEmpty() &&
        Pattern.matches(NUM_REGEX, stopFrame.getText());
    }

    @Override
    public void updateData () {
        // TODO Auto-generated method stub

    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // TODO Auto-generated method stub

    }

}
