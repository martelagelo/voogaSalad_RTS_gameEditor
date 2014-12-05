package view.editor.wizards;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import engine.actions.enumerations.ActionOptions;
import engine.actions.enumerations.ActionParameters;
import engine.actions.enumerations.ActionType;


/**
 * 
 * @author Joshua, Nishad
 *
 */
public class ActionWizard extends Wizard {

    private static final String EE_DELIMITER = "#";
    private static final String DELIMETER = "((?<="+EE_DELIMITER+")|(?="+EE_DELIMITER+"))";
	
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
        actionType.valueProperty().addListener((observable, oldValue, newValue) -> {
            actionChoice.setVisible(true);
            actionChoice.setItems(FXCollections.observableArrayList(ActionOptions.values()));
            actionChoice.valueProperty().addListener((o, oldVal, newVal) -> buildOptionedString(newVal));
        });
    }
    
    private void buildOptionedString (ActionOptions actionOption) {
    	String[] splitOnOptions = actionOption
    			.getMessage().split(DELIMETER);
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
