package view.editor;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.exceptions.CampaignNotFoundException;
import model.exceptions.LevelNotFoundException;
import model.state.LevelState;
import util.multilanguage.LanguagePropertyNotFoundException;
import util.multilanguage.MultiLanguageUtility;
import view.dialog.DialogBoxUtility;
import view.editor.wizards.Wizard;
import view.editor.wizards.WizardData;
import view.editor.wizards.WizardDataType;
import view.editor.wizards.WizardUtility;
import view.gui.GUIContainer;
import view.gui.GUIPanePath;
import view.runner.GameRunnerPaneController;
import engine.UI.EditorInputManager;
import engine.UI.RunnerInputManager;
import engine.gameRepresentation.evaluatables.actions.ActionWrapper;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;


/**
 * 
 * @author Jonathan Tseng
 * @author Nishad Agrawal
 *
 */
public class TabViewController extends GUIContainer {

    private final static String EDITOR_INPUT_KEY = "EditorInput";
    private final static String RUNNER_INPUT_KEY = "RunnerInput";
    private final static String RESET_KEY = "Reset";

    @FXML
    private VBox levelTrigger;
    @FXML
    private LevelTriggersViewController levelTriggerController;
    @FXML
    private StackPane gameRunnerPane;
    @FXML
    private GameRunnerPaneController gameRunnerPaneController;
    @FXML
    private BorderPane tabPane;
    @FXML
    private Button resetButton;
    @FXML
    private ToggleButton controllerToggle;
    @FXML
    private VBox vbox;

    private LevelState myLevel;

    public boolean isLevel(String campaign, String level) {
        try {
            return (myLevel == myMainModel.getLevel(campaign, level));
        }
        catch (LevelNotFoundException | CampaignNotFoundException e) {
            return false;
        }
    }
    
    public void updateModelToSave() {
        myLevel = gameRunnerPaneController.getLevelState();
    }
    
    private Consumer<Consumer<WizardData>> launchNestedWizard () {
        Consumer<Consumer<WizardData>> consumer =
                (cons) -> {
                    Wizard wiz =
                            WizardUtility.loadWizard(GUIPanePath.ACTION_WIZARD, new Dimension(400,
                                                                                              600));
                    addNumberAttributes(wiz);
                    Consumer<WizardData> bc = (data) -> {
                        myMainModel.createGoal(myLevel, data);
                        wiz.closeStage();
                    };
                    wiz.setSubmit(bc);
                };
        return consumer;
    }

    private void addNumberAttributes (Wizard wiz) {
        List<String> numberAttrs = myMainModel.getGameUniverse().
                getNumericalAttributes().stream().map(atr -> atr.getName())
                .collect(Collectors.toList());
        wiz.loadGlobalValues(numberAttrs);
    }

    public void setLevel (String campaign, String level) throws LevelNotFoundException,
                                                        CampaignNotFoundException {
        myLevel = myMainModel.getLevel(campaign, level);
        attachChildContainers(gameRunnerPaneController);
        startLevel();
        gameRunnerPaneController.setOnDone(e -> startLevel());
        controllerToggle.setSelected(true);
    }

    private void startLevel () {
        gameRunnerPaneController.setLevel(myLevel);
        controllerToggle.setSelected(true);
    }

    @Override
    public void modelUpdate () {
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
                    TriggerPair pair = new TriggerPair(act.getActionType(),
                                                       getByClassName(act.getActionClassName()),
                                                       act.getParameters());
                    triggers.add(pair);
                });
            });
        });
        levelTriggerController.updateTriggerList(triggers);
    }

    private ActionOptions getByClassName (String className) {
        return Arrays.asList(ActionOptions.values()).stream()
                .filter(action -> action.getClassString().equals(className))
                .collect(Collectors.toList()).get(0);
    }

    public class TriggerPair {
        public ActionType myActionType;
        public ActionOptions myAction;
        public String[] myParams;

        public TriggerPair (ActionType actionType, ActionOptions action, String[] params) {
            myActionType = actionType;
            myAction = action;
            myParams = params;
        }
    }

    @Override
    public void init () {
        levelTriggerController.setButtonAction(launchNestedWizard());
        levelTriggerController.setSelectedAction(modifyGoals());
        levelTriggerController.setDeleteAction(deleteGoal());
        try {
            resetButton.textProperty().bind(MultiLanguageUtility.getInstance()
                    .getStringProperty(RESET_KEY));
        }
        catch (LanguagePropertyNotFoundException e1) {
            DialogBoxUtility.createMessageDialog(e1.toString());
        }
        initToggle();
        controllerToggle.setSelected(false);
        resetButton.setOnAction(e -> startLevel());
    }

    private void initToggle () {
        controllerToggle
                .selectedProperty()
                .addListener( (observable, oldValue, newValue) -> {
                    try {
                        ObjectProperty<String> toggleText =
                                (!newValue) ?
                                          MultiLanguageUtility.getInstance()
                                                  .getStringProperty(RUNNER_INPUT_KEY)
                                          :
                                          MultiLanguageUtility
                                                  .getInstance()
                                                  .getStringProperty(EDITOR_INPUT_KEY);
                        controllerToggle.textProperty().bind(toggleText);
                        Class<?> inputManager =
                                (newValue) ? EditorInputManager.class
                                          : RunnerInputManager.class;
                        gameRunnerPaneController.setInputManager(inputManager);
                    }
                    catch (Exception e1) {
                        // do nothing
                    }
                });
    }

    private BiConsumer<Integer, String> modifyGoals () {
        BiConsumer<Integer, String> consumer =
                (Integer position, String oldValues) -> {
                    updateLevelTriggersView();
                    Wizard wiz =
                            WizardUtility.loadWizard(GUIPanePath.ACTION_WIZARD, new Dimension(400,
                                                                                              600));
                    String[] oldStrings = oldValues.split("\n");
                    WizardData oldData = new WizardData();
                    oldData.addDataPair(WizardDataType.ACTIONTYPE, oldStrings[0]);
                    oldData.addDataPair(WizardDataType.ACTION, oldStrings[1]);
                    oldData.addDataPair(WizardDataType.ACTION_PARAMETERS,
                                        extractParamString(oldStrings));
                    wiz.launchForEdit(oldData);
                    Consumer<WizardData> bc = createInternalConsumer(position, wiz);
                    wiz.setSubmit(bc);
                };
        return consumer;
    }

    private Consumer<WizardData> createInternalConsumer (Integer position, Wizard wiz) {
        Consumer<WizardData> bc = (data) -> {
            Map<ActionType, List<ActionWrapper>> actions =
                    myLevel.getGoals().get(position).getActions();
            actions.clear();
            List<ActionWrapper> actionValue = new ArrayList<>();
            String[] params =
                    data.getValueByKey(WizardDataType.ACTION_PARAMETERS)
                            .split(",");
            ActionWrapper wrapper =
                    new ActionWrapper(ActionType.valueOf(data
                            .getValueByKey(WizardDataType.ACTIONTYPE)),
                                      ActionOptions.valueOf(data
                                              .getValueByKey(WizardDataType.ACTION)),
                                      params);
            actionValue.add(wrapper);
            actions.put(ActionType
                    .valueOf(data.getValueByKey(WizardDataType.ACTIONTYPE))
                        , actionValue);
            updateLevelTriggersView();
            wiz.closeStage();
        };
        return bc;
    }

    private String extractParamString (String[] oldStrings) {
        String[] params = oldStrings[2].substring(1, oldStrings[2].length() - 1).split(",");        
        StringBuilder sb = new StringBuilder();
        Arrays.asList(params).forEach(param -> sb.append(param.trim() + ","));
        return sb.toString();
    }

    private Consumer<Integer> deleteGoal () {
        Consumer<Integer> consumer = (position) -> {
            if (position > -1) {
                myMainModel.removeGoal(myLevel, position);
            }
        };
        return consumer;
    }

    @Override
    public Node getRoot () {
        return tabPane;
    }

}
