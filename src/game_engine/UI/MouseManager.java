package game_engine.UI;

import java.util.Observable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * 
 * The class for managing the clicks the user makes. Takes in click information
 * and allows observers to get that information.
 * 
 * @author John
 *
 */
public class MouseManager extends Observable {
	private MouseEvent lastEvent;
	private double xLoc, yLoc;

	/**
	 * Update the click manager based on a new mouse event
	 * 
	 * @param event
	 * @param xMap
	 * @param yMap
	 */
	public void clicked(MouseEvent event, double xMap, double yMap) {
		this.xLoc = xMap;
		this.yLoc = yMap;
		this.lastEvent = event;

		setChanged();
		notifyObservers();
	}

	public Point2D getMapLoc() {
		return new Point2D(xLoc, yLoc);
	}

	public MouseEvent getLastClick() {
		return lastEvent;
	}
}
