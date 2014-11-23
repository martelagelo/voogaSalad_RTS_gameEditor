package game_engine.visuals;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * The minimap for the application
 * 
 * @author Michael D.
 *
 */
public class MiniMap {

	private Group myMiniMap;
	private Canvas myDisplay;
	private GraphicsContext myGraphicsContext;
	private List<Point2D> gameElementPoints;

	/**
	 * Constructor for the MiniMap
	 */
	public MiniMap() {
		myMiniMap = new Group();
		myDisplay = new Canvas(300, 250);
		myGraphicsContext = myDisplay.getGraphicsContext2D();
		initializeDisplay();
		gameElementPoints = new ArrayList<Point2D>();
		myMiniMap.setLayoutX(0);
		myMiniMap.setLayoutY(0);
		myMiniMap.getChildren().add(myDisplay);
	}

	/**
	 * Gets the canvas for the minimap
	 * 
	 * @return The canvas representing the minimap
	 */
	public Canvas getDisplay() {
		return myDisplay;
	}

	public Group getMiniMap() {
		return myMiniMap;
	}

	private void moveUnits() {

	}

	private void initializeDisplay() {
		// myDisplay.setLayoutX(0);
		// myDisplay.setLayoutY(0);
		// myDisplay.setWidth(100);
		// myDisplay.setHeight(100);
		// myDisplay.setStyle("-fx-border-width: 3; -fx-border-color: black;" );
	}

	private void initializeGraphicsContext() {
		// myGraphicsContext.setFill(Color.WHITE);
		// myGraphicsContext.fillOval(10, 60, 30, 30);
		drawShapes(myGraphicsContext);
	}

	private void drawShapes(GraphicsContext gc) {
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);
		gc.strokeLine(40, 10, 10, 40);
		gc.fillOval(10, 60, 30, 30);
		gc.strokeOval(60, 60, 30, 30);
		gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
		gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210,
				210, 240, 240 }, 4);
		gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210,
				210, 240, 240 }, 4);
		gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] {
				210, 210, 240, 240 }, 4);
	}
}
