package game_engine.visuals;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

/**
 * The 
 * 
 * @author Michael
 *
 */
public class MiniMap {

	private Canvas myDisplay;
	private List<Point2D> gameElementPoints;
	
	public MiniMap() {
		myDisplay = new Canvas();
		initializeDisplay();
		gameElementPoints = new ArrayList<Point2D>();
	}
	
	public Canvas getDisplay() {
		return myDisplay;
	}
	
	private void moveUnits() {
		
	}
	
	private void initializeDisplay(){
		myDisplay.setLayoutX(0);
		myDisplay.setLayoutY(0);
		myDisplay.setWidth(100);
		myDisplay.setHeight(100);
		myDisplay.setStyle("-fx-border-width: 3; -fx-border-color: black");;
	}
	
}
