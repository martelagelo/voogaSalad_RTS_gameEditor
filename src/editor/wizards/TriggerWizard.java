package editor.wizards;

import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 
 * @author joshua
 *
 */
public class TriggerWizard extends Wizard{
	
	@FXML private TextField condition;
	@FXML private TextField action;
	
	/**
	 * Takes a biconsumer that takes in the condition and action
	 * string from the trigger wizard. The biconsumer can then
	 * do with those strings whatever it likes without revealing
	 * its internals to the TriggerWizard
	 * 
	 * @param BiConsumer c, a biconsumer which will use the the
	 * condition and action text fields
	 */
	public void setSubmit(BiConsumer<String, String> c){
		save.setOnAction(e -> c.accept(condition.getText(), action.getText()));
	}

}
