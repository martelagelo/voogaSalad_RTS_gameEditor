package editor;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIContainer;
import view.WizardUtility;
import editor.wizards.Wizard;
import editor.wizards.WizardData;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class TabViewController extends GUIContainer {

    @FXML
    private VBox levelTrigger;
    @FXML
    private LevelTriggersViewController levelTriggerController;
    @FXML
    private BorderPane gameRunnerPane;
    @FXML
    private BorderPane tabPane;

    private static final String TRIGGER_WIZARD = "/editor/wizards/guipanes/TriggerWizard.fxml";

    private Consumer<Consumer<WizardData>> launchNestedWizard () {
        Consumer<Consumer<WizardData>> consumer = (cons) -> {
            Wizard wiz = WizardUtility.loadWizard(TRIGGER_WIZARD, new Dimension(600, 300));
            Consumer<WizardData> bc = (data) -> {
                myMainModel.addGoal(data);
                wiz.getStage().close();
            };
            wiz.setSubmit(bc);
        };
        return consumer;
    }

    @Override
    public void update () {
        updateLevelTriggersView();
    }

    /**
     * This is the code required to filter the goals by type within the level goals view
     */
    private void updateLevelTriggersView() {
        List<GameElementState> goals = myMainModel.getCurrentLevel().getGoals();
        List<TriggerPair> triggers = new ArrayList<>();
        for (GameElementState ges: goals) {
            for (String actionType: ges.getActions().keySet()) {
                for (String action: ges.getActions().get(actionType))  {
                    triggers.add(new TriggerPair(actionType, action));
                }
            }
        }
        levelTriggerController.updateTriggerList(triggers);
    }
    
    public class TriggerPair {
        public String myActionType;
        public String myAction;
        
        public TriggerPair(String actionType, String action) {
            myActionType = actionType;
            myAction = action;
        }
    }
    
    @Override
    public void init () {
        System.out.println(levelTriggerController == null);
        levelTriggerController.setButtonAction(launchNestedWizard());
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }

}
