package editor;

import java.awt.Dimension;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIContainer;
import view.WizardUtility;
import editor.wizards.Wizard;
import editor.wizards.WizardData;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class LevelTriggersViewController extends GUIContainer {

    @FXML
    private VBox levelTriggersView;
    @FXML
    private Button newLevelTrigger;
    @FXML
    private ListView levelTriggers;
    
    private static final String TRIGGER_WIZARD = "/editor/wizards/guipanes/TriggerWizard.fxml";

    @Override
    public void update () {

    }

    @Override
    public void init () {
        newLevelTrigger.setOnAction(e -> launchNestedWizard(TRIGGER_WIZARD));
    }

    @Override
    public Node getRoot () {
        return levelTriggersView;
    }
    
    private void launchNestedWizard (String s) {
        Wizard wiz = WizardUtility.loadWizard(s, new Dimension(600, 300));
        Consumer<WizardData> bc = (data) -> {
            System.out.println(data);
            wiz.getStage().close();
        };
        wiz.setSubmit(bc);
    }

}
