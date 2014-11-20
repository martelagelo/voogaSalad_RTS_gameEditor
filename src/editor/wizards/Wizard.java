package editor.wizards;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import view.GUIController;

/**
 * 
 * @author joshua
 *
 */
public class Wizard implements GUIController {
	
	@FXML protected Node root;
	@FXML protected Button save;
	
	/**
	 * Returns the root of the wizard's scene
	 * 
	 * @return Node root - the root of the scene
	 */
	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public void initialize() {
		
		
	}
}
