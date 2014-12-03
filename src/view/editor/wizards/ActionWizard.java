package view.editor.wizards;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import action.Action;
import action.ActionType;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class ActionWizard extends Wizard {

    @FXML
    private ComboBox<ActionType> actionType;
    @FXML
    private TextField action;
    @FXML
    private ComboBox<Action> actionChoice;   

    @Override
    public boolean checkCanSave () {
        return actionType.getSelectionModel().getSelectedItem() != null && !action.getText().isEmpty();
    }

    @Override
    public void updateData () {
        setDataType(WizardDataType.TRIGGER);
        addToData(WizardDataType.ACTIONTYPE, actionType.getSelectionModel().getSelectedItem().name());
        addToData(WizardDataType.ACTION, action.getText());
    }
    
    @Override
    public void initialize () {
        super.initialize();     
        actionType.setItems(FXCollections.observableArrayList(ActionType.values()));
        actionType.valueProperty().addListener(new ChangeListener<ActionType>() {
            @Override public void changed(ObservableValue ov, ActionType t, ActionType t1) {
            	System.out.println(t1.name());
                actionChoice.setItems(FXCollections.observableArrayList(t1.getActions()));
                actionChoice.valueProperty().addListener(new ChangeListener<Action>() {
                    @Override
                    public void changed (ObservableValue<? extends Action> observable,
                                         Action oldValue,
                                         Action newValue) {
                        System.out.println(newValue.name());
                        String s = "";
                        for (String temp: newValue.getTemplate()) {
                            s = s + temp + "###";
                        }
                        action.setText(s);
                    }
                });
                }    
               
          });
    }

    @Override
    public void launchForEdit (WizardData oldValues) {        
        actionType.getSelectionModel().select(ActionType.valueOf(oldValues.getValueByKey(WizardDataType.ACTIONTYPE)));
        action.setText(oldValues.getValueByKey(WizardDataType.ACTION));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing        
    }
}
