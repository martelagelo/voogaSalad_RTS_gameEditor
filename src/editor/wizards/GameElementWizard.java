package editor.wizards;

import gamemodel.GameElementStateFactory;
import java.awt.Dimension;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import view.WizardUtility;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class GameElementWizard extends Wizard {

    private static final String NUM_ATTR_WIZARD =
            "/editor/wizards/guipanes/NumberAttributeWizard.fxml";
    private static final String STRING_ATTR_WIZARD =
            "/editor/wizards/guipanes/StringAttributeWizard.fxml";
    private static final String TRIGGER_WIZARD = "/editor/wizards/guipanes/TriggerWizard.fxml";
    @FXML
    private TextField name;
    @FXML
    private Button trigger;
    @FXML
    private Button stringAttribute;
    @FXML
    private Button numberAttribute;

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchTriggerEditor () {
        launchNestedWizard(TRIGGER_WIZARD);
    }    

    /**
     * Launches a String Attribute Wizard
     * 
     */
    private void launchStringAttributeEditor () {
        launchNestedWizard(STRING_ATTR_WIZARD);
    }

    /**
     * Launches a Nuumber Attribute Wizard
     * 
     */
    private void launchNumberAttributeEditor () {
        launchNestedWizard(NUM_ATTR_WIZARD);
    }

    private void launchNestedWizard (String s) {
        Wizard wiz = WizardUtility.loadWizard(s, new Dimension(600, 300));        
        Consumer<WizardData> bc = (data) -> {
            addWizardData(data);
            wiz.getStage().close();
        };
        wiz.setSubmit(bc);
    }
    
    /**
     * Binds all the listeners to the scene
     * 
     */
    @Override
    public void initialize () {
        super.initialize();
        trigger.setOnAction(e -> launchTriggerEditor());
        stringAttribute.setOnAction(e -> launchStringAttributeEditor());
        numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
    }

    @Override
    public boolean checkCanSave () {
        return !name.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataName(GameElementStateFactory.GAME_ELEMENT);
        addToData(GameElementStateFactory.NAME, name.getText());
    }

}
