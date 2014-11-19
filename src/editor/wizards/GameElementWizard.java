package editor.wizards;

import gamemodel.GameElementInfoBundle;

import java.util.Observable;
import java.util.function.Consumer;

import view.GUIController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

public class GameElementWizard  implements GUIController{
	
	@FXML private TextField name;
	@FXML private SplitPane root;
	@FXML private Button trigger;
	@FXML private Button attribute;
	@FXML private Button save;
	private GameElementInfoBundle myGameElementInfoBundle;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Node getRoot() {
		return root;
	}

	@Override
	public String[] getCSS(){ 
		return new String[0];
	}
	
	public void setSubmit(Consumer<GameElementInfoBundle> c){
		save.setOnAction(e -> c.accept(myGameElementInfoBundle));
	}

	@Override
	public void initialize() {
		myGameElementInfoBundle = new GameElementInfoBundle();
		
	}
}
