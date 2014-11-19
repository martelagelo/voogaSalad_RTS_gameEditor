package editor.wizards;

import java.util.function.BiConsumer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 
 * @author joshua
 *
 */
public class NumberAttributeWizard extends Wizard{
	@FXML private TextField key;
	@FXML private TextField numberValue;
	
	/**
	 * Takes in a biconsumer that takes two strings
	 * and uses them. Intended to  hide the internals
	 * of what is done with those two strings from the
	 * NumberAttributeWizard
	 * 
	 * @param BiConsumer c, a biconsumer that takes in
	 * two strings
	 */
	public void setSubmit(BiConsumer<String, String> c){
		save.setOnAction(e -> c.accept(key.getText(), numberValue.getText()));
	}
}