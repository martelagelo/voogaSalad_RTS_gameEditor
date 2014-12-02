package view.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class ActionWizard extends Wizard {

    private static final String ACTION_TYPES_PROPS = "resources.properties.engine.ActionTypes";
    @FXML
    private ComboBox<String> actionType;
    @FXML
    private TextField action;
    
    private ResourceBundle actionTypeBundle;

    @Override
    public boolean checkCanSave () {
        return actionType.getSelectionModel().getSelectedItem() != null && !action.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.TRIGGER);
        addToData(WizardDataType.ACTIONTYPE, actionType.getSelectionModel().getSelectedItem());
        addToData(WizardDataType.ACTION, action.getText());
    }
    
    @Override
    public void initialize () {
        super.initialize();
        actionTypeBundle = ResourceBundle.getBundle(ACTION_TYPES_PROPS);        
        List<String> actionTypes = new ArrayList<>();                 
        actionTypeBundle.keySet().forEach((value) -> {
            actionTypes.add(actionTypeBundle.getString(value));
        });        
        actionType.setItems(FXCollections.observableArrayList(actionTypes));
    }

    @Override
    public void launchForEdit (WizardData oldValues) {
        actionType.getSelectionModel().select(oldValues.getValueByKey(WizardDataType.ACTIONTYPE));
        action.setText(oldValues.getValueByKey(WizardDataType.ACTION));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing        
    }
}
