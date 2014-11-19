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

public class GameElementWizard  extends Wizard{
	
	@FXML private TextField name;
	@FXML private Button trigger;
	@FXML private Button attribute;
	private GameElementInfoBundle myGameElementInfoBundle;

	public void setSubmit(Consumer<GameElementInfoBundle> c){
		save.setOnAction(e -> c.accept(myGameElementInfoBundle));
	}

	@Override
	public void initialize() {
		myGameElementInfoBundle = new GameElementInfoBundle();
	}
}
