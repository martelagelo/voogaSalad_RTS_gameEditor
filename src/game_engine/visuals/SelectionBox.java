package game_engine.visuals;

import game_engine.UI.ClickManager;
import game_engine.UI.MouseDragManager;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The class for creating the rectangle that the user draws during a game with
 * their cursor. Extends observable so that other classes that want to be
 * notified of unit selection can see where the box was drawn
 * 
 * @author John
 *
 */
public class SelectionBox extends Observable implements Observer {
	private Rectangle myRectangle;
	private double pressedX, pressedY;
	private double[] points = new double[4];
	private MouseEvent lastEvent;

	public SelectionBox() {
		myRectangle = new Rectangle();
		myRectangle.setStroke(Color.RED);
		myRectangle.setStrokeWidth(2);
		myRectangle.setFill(Color.TRANSPARENT);
	}

	public void released(double topLeftX, double topLeftY, double bottomRightX,
			double bottomRightY) {
		points = new double[] { topLeftX, topLeftY, bottomRightX, bottomRightY };
		setChanged();
		notifyObservers();
		System.out.println("SelectionBox: (" + topLeftX + ", " + topLeftY
				+ ") , (" + bottomRightX + ", " + bottomRightY + ")");
	}

	public double[] getPoints() {
		return points;
	}

	public Rectangle getBox() {
		return myRectangle;
	}

	public void resetBox() {
		myRectangle.setVisible(false);
		setSize(0, 0);
		setLocation(0, 0);
	}

	public void setSize(double width, double height) {
		myRectangle.setWidth(width);
		myRectangle.setHeight(height);
	}

	public void setX(double x) {
		myRectangle.setX(x);
	}

	public void setY(double y) {
		myRectangle.setY(y);
	}

	public void setLocation(double x, double y) {
		setX(x);
		setY(y);
	}

	public void setVisible(boolean b) {
		myRectangle.setVisible(b);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MouseDragManager) {
			MouseDragManager dragManager = ((MouseDragManager) o);
			reactToDrag(dragManager);
		} else if (o instanceof ClickManager) {
			ClickManager clickManager = ((ClickManager) o);
			MouseEvent e = clickManager.getLastClick();

			if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					resetBox();
					pressedX = e.getSceneX();
					pressedY = e.getSceneY();
					setLocation(e.getSceneX(), e.getSceneY());
				}
			} else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					setVisible(false);

					// do the math to determine the box's location relative to
					// the map, rather than
					// on the screen
					double xTranslate = clickManager.getMapLoc().getX()
							- e.getSceneX();
					double yTranslate = clickManager.getMapLoc().getY()
							- e.getSceneY();

					double xTop = myRectangle.getX() + xTranslate;
					double yTop = myRectangle.getY() + yTranslate;

					released(xTop, yTop, xTop + myRectangle.getWidth(), yTop
							+ myRectangle.getHeight());
				}
			}
		}
	}

	private void reactToDrag(MouseDragManager dragManager) {
		lastEvent = dragManager.getLastClick();
		if (lastEvent.isPrimaryButtonDown()) {
			setVisible(true);
			double newX = lastEvent.getSceneX();
			double newY = lastEvent.getSceneY();
			double difX = newX - pressedX;
			double difY = newY - pressedY;

			setSize(Math.abs(difX), Math.abs(difY));
			if (difX <= 0)
				setX(newX);
			if (difY <= 0)
				setY(newY);

		}
	}

	public MouseEvent getLastClick() {
		return lastEvent;
	}

}
