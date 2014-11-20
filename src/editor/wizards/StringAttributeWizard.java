package editor.wizards;

import java.util.function.BiConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 
 * @author joshua
 *
 */
public class StringAttributeWizard extends Wizard{
	@FXML private TextField key;
	@FXML private TextField stringValue;
	
	/**
	 * Takes in a biconsumer that uses the key
	 * and stringValue fields to do leverage the
	 * StringAttributeWizard without revealing
	 * its internals to the StringAttributeWizard
	 * 
	 * @param BiConsumer c, a consumer which takes
	 * in two strings
	 */
	public void setSubmit(BiConsumer<String, String> c){
		save.setOnAction(e -> c.accept(key.getText(), stringValue.getText()));
	}
}
