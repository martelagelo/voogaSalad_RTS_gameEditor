package view.editor.wizards;

import java.util.Arrays;
import java.util.List;

import engine.actions.Action;
import engine.actions.enumerations.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class ActionWizard extends Wizard {

	private final String EE_DELIMITER = "#";
	
    @FXML
    private ComboBox<ActionType> actionType;
    @FXML
    private ComboBox<ActionOptions> actionChoice;   
    @FXML
    private Text message;  
    @FXML
    private HBox options;

    @Override
    public boolean checkCanSave () {
        return actionType.getSelectionModel().getSelectedItem() != null;
    }

    @Override
    public void updateData () {
    	setWizardType(WizardType.TRIGGER);
        addToData(WizardDataType.ACTIONTYPE, actionType.getSelectionModel().getSelectedItem().name());
        // addToData(WizardDataType.ACTION, action.getText());
        addToData(WizardDataType.ACTION, "action");
    }
    
    @Override
    public void initialize () {
        super.initialize();
        actionChoice.setVisible(false);
        actionType.setItems(FXCollections.observableArrayList(ActionType.values()));
        actionType.valueProperty().addListener(new ChangeListener<ActionType>() {
            @Override public void changed(ObservableValue ov, ActionType t, ActionType t1) {
            	actionChoice.setVisible(true);
            	actionChoice.setItems(FXCollections.observableArrayList(ActionOptions.values()));
                actionChoice.valueProperty().addListener(new ChangeListener<ActionOptions>() {
                    @Override
                    public void changed (ObservableValue<? extends ActionOptions> observable,
                                         ActionOptions oldValue,
                                         ActionOptions newValue) {
                    	buildOptionedString(newValue);
                    }
                });
                }    
               
          });
    }
    
    private void buildOptionedString (ActionOptions actionOption) {
    	String[] splitOnOptions = actionOption
    			.getMessage()
    			.split("((?<="+EE_DELIMITER+")|(?="+EE_DELIMITER+"))");
    	int parameterIndex = 0;
    	List<ActionParameters> actionParameters = actionOption.getOperators();
    	options.getChildren().clear();
    	for(String s : splitOnOptions){
    		Node newOption;
    		if(s.equals(EE_DELIMITER)){
    			ComboBox<String> cb = new ComboBox<String>();
    			cb.setItems(FXCollections.observableArrayList(
    					actionParameters.get(parameterIndex).getOptions()
    				));
    			parameterIndex++;
    			newOption = cb;
    		}
    		else{
        		Text t = new Text(s);
        		newOption = t;
    		}
    		options.getChildren().add(newOption);
    	}
    }

    @Override
    public void launchForEdit (WizardData oldValues) {        
        actionType.getSelectionModel().select(ActionType.valueOf(oldValues.getValueByKey(WizardDataType.ACTIONTYPE)));
        // action.setText(oldValues.getValueByKey(WizardDataType.ACTION));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing        
    }
}
