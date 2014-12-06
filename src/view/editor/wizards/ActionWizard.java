package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionOptions;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionParameters;
import engine.gameRepresentation.evaluatables.actions.enumerations.ActionType;


/**
 * public ActionWrapper (String actionType, String actionName, String ... parameters)
 * 
 * @author Joshua, Nishad
 *
 */
public class ActionWizard extends Wizard {

    private static final String EE_DELIMITER = "#";
    private static final String DELIMETER = "((?<=" + EE_DELIMITER + ")|(?=" + EE_DELIMITER + "))";

    @FXML
    private ComboBox<ActionType> actionType;
    @FXML
    private ComboBox<ActionOptions> actionChoice;
    @FXML
    private Text message;
    @FXML
    private VBox options;

    private List<ComboBox<String>> dropdowns;

    @Override
    public boolean checkCanSave () {
        return actionType.getSelectionModel().getSelectedItem() != null &&
               actionChoice.getSelectionModel().getSelectedItem() != null &&
               dropdownsValid();
    }

    private boolean dropdownsValid () {
        if (dropdowns.size() == 0) return false;
        for (ComboBox<String> box : dropdowns) {
            if (box.getSelectionModel().getSelectedItem() == null ||
                box.valueProperty().getValue() == null) { return false; }
        }
        return true;
    }

    @Override
    public void updateData () {
        setWizardType(WizardType.TRIGGER);
        addToData(WizardDataType.ACTIONTYPE, actionType.getSelectionModel().getSelectedItem()
                .name());
        addToData(WizardDataType.ACTION, actionChoice.getSelectionModel().getSelectedItem().name());
        addToData(WizardDataType.ACTION_PARAMETERS, getDropdownText());
    }

    private String getDropdownText () {
        StringBuilder sb = new StringBuilder();
        for (ComboBox<String> box : dropdowns) {
            sb.append(box.getSelectionModel().getSelectedItem() + ",");
        }
        return sb.toString();
    }

    @Override
    public void initialize () {
        super.initialize();
        actionType.setItems(FXCollections.observableArrayList(ActionType.values()));
        actionChoice.setItems(FXCollections.observableArrayList(ActionOptions.values()));
        actionChoice.valueProperty()
                .addListener( (o, oldVal, newVal) -> buildOptionedString(newVal));
        dropdowns = new ArrayList<>();
    }

    private void buildOptionedString (ActionOptions actionOption) {
        String[] splitOnOptions = actionOption
                .getMessage().split(DELIMETER);
        int parameterIndex = 0;
        List<ActionParameters> actionParameters = actionOption.getOperators();
        options.getChildren().clear();
        dropdowns.clear();
        for (String s : splitOnOptions) {
            if (s.equals(EE_DELIMITER)) {
                ComboBox<String> cb = new ComboBox<String>();
                cb.setItems(FXCollections.observableArrayList(
                        actionParameters.get(parameterIndex).getOptions()
                        ));
                cb.setPromptText(actionParameters.get(parameterIndex).name());
                cb.setEditable(cb.getItems().size() == 0);
                parameterIndex++;
                dropdowns.add(cb);
                options.getChildren().add(cb);
            }
            else if (s.trim().length() > 0){
                Text t = new Text(s);
                options.getChildren().add(t);
            }
            
        }
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        actionType.getSelectionModel().select(ActionType.valueOf(oldValues
                .getValueByKey(WizardDataType.ACTIONTYPE)));
        actionChoice.getSelectionModel().select(ActionOptions.valueOf(oldValues
                .getValueByKey(WizardDataType.ACTION)));
        String[] params = oldValues.getValueByKey(WizardDataType.ACTION_PARAMETERS).split(",");
        for (int i = 0; i < dropdowns.size(); i++) {
            dropdowns.get(i).getSelectionModel().select(params[i]);
        }
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing
    }
}