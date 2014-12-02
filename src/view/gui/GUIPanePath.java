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
    NUMBER_ATTRIBUTE_WIZARD("/view/editor/wizards/guipanes/NumberAttributeWizard.fxml"),
    SELECTABLE_GAME_ELEMENT_WIZARD("/view/editor/wizards/guipanes/SelectableGameElementWizard.fxml"),
    STRING_ATTRIBUTE_WIZARD("/view/editor/wizards/guipanes/StringAttributeWizard.fxml"),
    ACTION_WIZARD("/view/editor/wizards/guipanes/ActionWizard.fxml"),

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
