package editor;

import editor.wizards.Wizard;
import editor.wizards.WizardData;
import editor.wizards.WizardDataType;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import gamemodel.exceptions.CampaignNotFoundException;
import gamemodel.exceptions.LevelNotFoundException;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import view.GUIContainer;
import view.WizardUtility;
import view.gamerunner.GameRunnerViewController;


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
    private GameRunnerViewController gameRunnerPaneController;
    @FXML
    private BorderPane tabPane;

    private static final String TRIGGER_WIZARD = "/editor/wizards/guipanes/TriggerWizard.fxml";

    private LevelState myLevel;

    private Consumer<Consumer<WizardData>> launchNestedWizard () {
        Consumer<Consumer<WizardData>> consumer = (cons) -> {
            Wizard wiz = WizardUtility.loadWizard(TRIGGER_WIZARD, new Dimension(300, 300));
            Consumer<WizardData> bc = (data) -> {
                myMainModel.addGoal(data);
                wiz.getStage().close();
            };
            wiz.setSubmit(bc);
        };
        return consumer;
    }

    public void setLevel (String campaign, String level) throws LevelNotFoundException,
                                                        CampaignNotFoundException {
        myLevel = myMainModel.getLevel(campaign, level);
    }

    @Override
    public void update () {
        updateLevelTriggersView();
    }

    /**
     * This is the code required to filter the goals by type within the level goals view
     */
    private void updateLevelTriggersView () {
        List<TriggerPair> triggers = new ArrayList<>();
        myLevel.getGoals().forEach( (ges) -> {
            ges.getActions().forEach( (actionType, actions) -> {
                actions.forEach( (act) -> {
                    triggers.add(new TriggerPair(actionType, act));
                });
            });
        });
        levelTriggerController.updateTriggerList(triggers);
    }

    public class TriggerPair {
        public String myActionType;
        public String myAction;

        public TriggerPair (String actionType, String action) {
            myActionType = actionType;
            myAction = action;
        }
    }

    @Override
    public void init () {
        levelTriggerController.setButtonAction(launchNestedWizard());
        levelTriggerController.setSelectedAction(modifyGoals());
        levelTriggerController.setDeleteAction(deleteGoal());
    }

    private BiConsumer<Integer, String> modifyGoals () {
        BiConsumer<Integer, String> consumer = (Integer position, String oldValues) -> {
            updateLevelTriggersView();
            Wizard wiz = WizardUtility.loadWizard(TRIGGER_WIZARD, new Dimension(300, 300));
            // TODO: THIS SHOULD BE CLEANED UP TO MATCH OTHER WIZARDS
                String[] oldStrings = oldValues.split("\n");
                WizardData oldData = new WizardData();
                oldData.addDataPair(WizardDataType.ACTIONTYPE, oldStrings[0]);
                oldData.addDataPair(WizardDataType.ACTION, oldStrings[1]);
                wiz.launchForEdit(oldData);
                Consumer<WizardData> bc =
                        (data) -> {
                            Map<String, List<String>> actions =
                                    myLevel.getGoals().get(position).getActions();
                            actions.clear();
                            List<String> actionValue = new ArrayList<>();
                            actionValue.add(data.getValueByKey(WizardDataType.ACTION));
                            actions.put(data.getValueByKey(WizardDataType.ACTIONTYPE), actionValue);
                            updateLevelTriggersView();
                            wiz.getStage().close();
                        };
                wiz.setSubmit(bc);
            };
        return consumer;
    }

    private Consumer<Integer> deleteGoal () {
        Consumer<Integer> consumer = (position) -> {
            if (position > -1) {
                myMainModel.removeGoal(position);
            }
        };
        return consumer;
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }

}
