package game_editor;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class GUIPaneGenerator {

	public GUIPaneGenerator () {
		
	}
	
	public GridPane splashPage () throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("GUIPanes/SplashPage.fxml"));
		GridPane gp = new GridPane();
		gp.add(root,0, 0);
		return gp;
	}
}
