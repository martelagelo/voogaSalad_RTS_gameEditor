package view.gui;



/**
 * 
 * @author Jonathan Tseng, Nishad
 *
 */
public enum GUIPanePath {
    // Runner
    RUNNER_PANE("/view/runner/GameRunnerPane.fxml"),
    // editor
    INFO_BOX("/view/editor/guipanes/DescribableInfoBox.fxml"),
    ELEMENT_ACCORDION("/view/editor/guipanes/EditorElementAccordion.fxml"),
    EDITOR_MENU_BAR("/view/editor/guipanes/EditorMenuBar.fxml"),
    EDITOR_TAB_VIEW("/view/editor/guipanes/EditorTabView.fxml"),
    GAME_ELEMENT_DROP_DOWN("/view/editor/guipanes/GameElementDropDown.fxml"),
    LEVEL_TRIGGER_VIEW("/view/editor/guipanes/EditorTriggerView.fxml"),
    PROJECT_EXPLORER_VIEW("/view/editor/guipanes/ProjectExplorerView.fxml"),

    // wizards
    CAMPAIGN_WIZARD("/view/editor/wizards/guipanes/CampaignWizard.fxml"),
    LEVEL_WIZARD("/view/editor/wizards/guipanes/LevelWizard.fxml"),
    WIDGET_WIZARD("/view/editor/wizards/guipanes/WidgetWizard.fxml"),
    PARTICIPANT_WIZARD("/view/editor/wizards/guipanes/ParticipantWizard.fxml"),
    NUMBER_ATTRIBUTE_WIZARD("/view/editor/wizards/guipanes/NumberAttributeWizard.fxml"),    
    GAME_ELEMENT_WIZARD("/view/editor/wizards/guipanes/GameElementWizard.fxml"),
    STRING_ATTRIBUTE_WIZARD("/view/editor/wizards/guipanes/StringAttributeWizard.fxml"),
    ACTION_WIZARD("/view/editor/wizards/guipanes/ActionWizard.fxml"),
    POSITION_WIZARD("/view/editor/wizards/guipanes/PositionWizard.fxml"),
    ANIMATION_WIZARD("/view/editor/wizards/guipanes/AnimationWizard.fxml"),
    BOUNDS_WIZARD("/view/editor/wizards/guipanes/BoundsWizard.fxml"),

    // dialog
    DIALOG_BOX("/view/dialog/DialogBox.fxml");

    private String myFilePath;

    private GUIPanePath (String filePath) {
        myFilePath = filePath;
    }

    public String getFilePath () {
        return myFilePath;
    }

}
