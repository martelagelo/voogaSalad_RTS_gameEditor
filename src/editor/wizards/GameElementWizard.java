package editor.wizards;

import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.GUILoadStyleUtility;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class GameElementWizard extends Wizard {

    private static final String NUM_ATTR_WIZARD = "/editor/wizards/guipanes/NumberAttributeWizard.fxml";
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
    protected WizardData myWizardData;
    private GUILoadStyleUtility loadStyleUtility;

    /**
     * Launches a TriggerEditorWizard
     * 
     */
    private void launchTriggerEditor () {
        TriggerWizard TW =
                (TriggerWizard) loadStyleUtility.generateGUIPane(TRIGGER_WIZARD);
        Scene myScene = new Scene((Parent) TW.getRoot(), 600, 300);
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
        Consumer<WizardData> bc = (data) -> {
            myWizardData.addWizardData(data);
            System.out.println(data);
            s.close();
        };
        TW.setSubmit(bc);
    }

    /**
     * Launches a String Attribute Wizard
     * 
     * TODO: Jonathan is cleaning up what needs to be
     * done to launch a wizard. There is repeated
     * code here that must be cleaned up
     */
    private void launchStringAttributeEditor () {
        StringAttributeWizard SAW =
                (StringAttributeWizard) loadStyleUtility.generateGUIPane(STRING_ATTR_WIZARD);
        Scene myScene = new Scene((Parent) SAW.getRoot(), 600, 300);
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
        Consumer<WizardData> bc = (data) -> {
            myWizardData.addWizardData(data);
            System.out.println(data);
            s.close();
        };
        SAW.setSubmit(bc);
    }

    /**
     * Launches a Nuumber Attribute Wizard
     * 
     * TODO: Jonathan is cleaning up what needs to be
     * done to launch a wizard. There is repeated
     * code here that must be cleaned up
     */
    private void launchNumberAttributeEditor () {
        NumberAttributeWizard NAW =
                (NumberAttributeWizard) loadStyleUtility
                        .generateGUIPane(NUM_ATTR_WIZARD);
        Scene myScene = new Scene((Parent) NAW.getRoot(), 600, 300);
        Stage s = new Stage();
        s.setScene(myScene);
        s.show();
        Consumer<WizardData> bc = (data) -> {
            myWizardData.addWizardData(data);
            System.out.println(data);
            s.close();
        };
        NAW.setSubmit(bc);
    }

    /**
     * Binds all the listeners to the scene
     * 
     */
    @Override
    public void initialize () {
        super.initialize();
        loadStyleUtility = new GUILoadStyleUtility();
        myWizardData = new WizardData();
        trigger.setOnAction(e -> launchTriggerEditor());
        stringAttribute.setOnAction(e -> launchStringAttributeEditor());
        numberAttribute.setOnAction(e -> launchNumberAttributeEditor());
    }

    @Override
    public boolean checkCanSave () {
        // also requires an image
        return !name.getText().isEmpty();
    }

    @Override
    public void updateData () {
        addToData("Name", name.getText());
    }

}
