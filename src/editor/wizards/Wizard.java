package editor.wizards;

import gamemodel.GameElementInfoBundle;

import java.util.Observable;
import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import view.GUIController;

public class Wizard implements GUIController{
	
	@FXML protected Node root;
	@FXML protected Button save;
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public String[] getCSS(){ 
		return new String[0];
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
