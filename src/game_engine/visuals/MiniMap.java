package game_engine.visuals;

import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
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

	private static final double MINIMAP_WIDTH = 250;
	private static final double MINIMAP_HEIGHT = 250;
	public static final double FIELD_WIDTH = 2000;
    public static final double FIELD_HEIGHT = 2000;
    private static final double X_SCALE = FIELD_WIDTH / MINIMAP_WIDTH;
    private static final double Y_SCALE = FIELD_HEIGHT / MINIMAP_HEIGHT;
    
	private Scene myScene;
	private Canvas myDisplay;
	private GraphicsContext myGraphicsContext;
	//private List<Point2D> gameElementPoints;
	private List<SelectableGameElement> gameUnits;

	/**
	 * Constructor for the MiniMap
	 */
	public MiniMap(Scene SS) {
		myScene = SS;
		myDisplay = new Canvas();
		myGraphicsContext = myDisplay.getGraphicsContext2D();
		initializeDisplay();
		initializeGraphicsContext();
		//gameElementPoints = new ArrayList<Point2D>();
	}

	/**
	 * Gets the canvas for the minimap
	 * 
	 * @return The canvas representing the minimap
	 */
	public Canvas getDisplay() {
		return myDisplay;
	}
	
	public void setUnits(List<SelectableGameElement> units) {
		gameUnits = units;
		//populateGamePointsList();
		//moveUnits();
	}
	

	public void moveUnits() {
		initializeGraphicsContext();
		myGraphicsContext.setFill(Color.BLACK);
		for(SelectableGameElement SGE: gameUnits) {
			myGraphicsContext.fillOval(SGE.getLocation().getX() / X_SCALE, SGE.getLocation().getY() / Y_SCALE, 3, 3);
		}
	}

	private void initializeDisplay() {
		myDisplay.setLayoutX(0);
		myDisplay.setLayoutY(0);
		myDisplay.setWidth(MINIMAP_WIDTH);
		myDisplay.setHeight(MINIMAP_HEIGHT);
		myDisplay.setOpacity(0.5);
	}

	private void initializeGraphicsContext() {
		myGraphicsContext.setFill(Color.WHITE);
		myGraphicsContext.setStroke(Color.BLACK);
		myGraphicsContext.setLineWidth(5);
		myGraphicsContext.fillRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, 40, 40);
		myGraphicsContext.strokeRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, 40, 40);
		//myGraphicsContext
		//myGraphicsContext
		//drawShapes(myGraphicsContext);
	}
	
//	private void populateGamePointsList() {
//		for(SelectableGameElement SGE: gameUnits) {
//			gameElementPoints.add(SGE.getLocation());
//		}
//	}

	private void drawShapes(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		//gc.strokeLine(40, 10, 10, 40);
		//gc.fillOval(10, 60, 30, 30);
		//gc.strokeOval(60, 60, 30, 30);
		gc.fillRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, 40, 40);
		gc.strokeRoundRect(0, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT, 40, 40);
		//gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
		//gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
		//gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
		//gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
		//gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
		//gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
		gc.fillPolygon(new double[] { 10, 40, 10, 40 }, new double[] { 210,
				210, 240, 240 }, 4);
		gc.strokePolygon(new double[] { 60, 90, 60, 90 }, new double[] { 210,
				210, 240, 240 }, 4);
		gc.strokePolyline(new double[] { 110, 140, 110, 140 }, new double[] {
				210, 210, 240, 240 }, 4);
	}
}
