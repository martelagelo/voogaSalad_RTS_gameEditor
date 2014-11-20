package editor.wizards;

import java.util.Observable;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import view.GUIController;

/**
 * 
 * @author joshua
 *
 */
public class Wizard implements GUIController{
	
	@FXML protected Node root;
	@FXML protected Button save;
	
	/**
	 * TODO- Jonathan is modifying the update super class
	 * method to get rid of it
	 */
	@Override
	public void update(Observable o, Object arg) {
		
	}

	/**
	 * Returns the root of the wizard's scene
	 * 
	 * @return Node root - the root of the scene
	 */
	@Override
	public Node getRoot() {
		return root;
	}

	/**
	 * TODO return the custom CSS for the wizard
	 */
	@Override
	public String[] getCSS(){ 
		return new String[0];
	}

	/**
	 * TODO- Jonathan is modifying the update super class
	 * method to get rid of it
	 */
	@Override
	public void initialize() {
		
		
	}
}
