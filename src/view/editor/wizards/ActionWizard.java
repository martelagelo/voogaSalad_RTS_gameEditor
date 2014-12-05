package view.editor.wizards;

import java.util.List;

import engine.actions.Action;
import engine.actions.ActionType;
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
    private TextField action;
    @FXML
    private ComboBox<Action> actionChoice;   
    @FXML
    private HBox options;

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
        buildOptionedString("YO#NISHADDIS#B#DA#SHIT");
        actionType.setItems(FXCollections.observableArrayList(ActionType.values()));
        actionType.valueProperty().addListener(new ChangeListener<ActionType>() {
            @Override public void changed(ObservableValue ov, ActionType t, ActionType t1) {
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
    
    private void buildOptionedString (String opString) {
    	String[] splitOnOptions = opString.split("((?<=#)|(?=#))");
    	for(String s : splitOnOptions){
    		Node newOption;
    		if(s.equals(EE_DELIMITER)){
    			ComboBox cb = new ComboBox();
    			cb.setItems(FXCollections.observableArrayList(ActionType.values()));
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
        action.setText(oldValues.getValueByKey(WizardDataType.ACTION));
    }

    @Override
    public void loadGlobalValues (List<String> values) {
        // do nothing        
    }
}
