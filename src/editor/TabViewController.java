package editor;

import java.awt.Dimension;
import java.util.List;
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
        System.out.println("here");
        updateLevelTriggersView();
    }

    private void updateLevelTriggersView() {
        List<GameElementState> goals = myMainModel.getCurrentLevel().getGoals();
        List<TriggerPair> triggers = goals.stream().map((element) -> {
            //TODO: fix this to actually get the goals
            return new TriggerPair("cond", "action");
        }).collect(Collectors.toList());
        levelTriggerController.updateTriggerList(triggers);
    }
    
    public class TriggerPair {
        public String myCondition;
        public String myAction;
        
        public TriggerPair(String condition, String action) {
            myCondition = condition;
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
