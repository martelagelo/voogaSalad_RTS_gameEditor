package view.editor.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import engine.action.ActionType;
import view.gui.ActionTypes;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private static final String ACTION_ROOT = "resources.properties.engine.actions.";
    private static final String ACTION_TYPES_PROPS = "ActionTypes";
    private static final String COLLISION_ACTION_TYPES_PROPS = "CollisionActionTypes";
    @FXML
    private ComboBox<String> actionType;
    @FXML
    private TextField action;
    @FXML
    private ComboBox<String> actionChoice;
    
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
        List<String> actionTypes = Arrays.asList(
         		engine.action.ActionType.values())
         		.stream()
         		.map(a -> a.name())
         		.collect(Collectors.toList());       
        actionType.setItems(FXCollections.observableArrayList(actionTypes));
        actionType.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
            	System.out.println(engine.action.ActionType.valueOf(t1));
                List<String> actionChoices =Arrays.asList(
                 		engine.action.ActionType.valueOf(t1).values())
                 		.stream()
                 		.map(a -> a.name())
                 		.collect(Collectors.toList());
                actionChoice.setItems(FXCollections.observableArrayList(actionChoices));
                }    
               
          });
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
