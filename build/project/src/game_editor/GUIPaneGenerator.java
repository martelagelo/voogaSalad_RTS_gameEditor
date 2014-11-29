package game_editor;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;


/**
 * 
 * @author Joshua Miller
 *
 *         A class for the generation of GUIPanes based on FXML files
 */
public class GUIPaneGenerator {

	public GUIPaneGenerator () {
		
	}
	
	/**
	 * A method to return the name splash page grid pane
	 *
	 * @return gp a GridPane of the splash page
	 * @throws Exception 
	 */
	public GridPane generateGridPane (String URL, int row, int col) throws Exception  {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(URL));
			GridPane gp = new GridPane();
			gp.add(root,row, col);
			return gp;
		} catch (IOException e) {
			throw new Exception();
		}
	}
}
