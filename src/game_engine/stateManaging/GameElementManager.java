package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.GameElement;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.UI.ClickManager;
import game_engine.UI.KeyboardManager;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.Dimension;
import game_engine.visuals.SelectionBox;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;


/**
 * A manager for selecting, deselecting, and interacting with game elements
 *
 * @author John, Steve, Michael D.
 *
 */
public class GameElementManager implements Observer {

    private Level myLevel;

    public GameElementManager (Level level) {
        myLevel = level;
    }

    /**
     * Get all the game elements of a given type
     *
     * @param typeName
     *        the name of the type of the game elements
     * @return all the game elements with the given type
     */
    public List<GameElementState> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .map(o -> o.getState()).collect(Collectors.toList());
    }

    public void addElementToLevel (String typeName) {
        // TODO: add factories
    }

    /**
     * Select all the elements in a given rectangle
     *
     * @param rectPoints
     *        the points in the rectangle surrounding the player's units
     */
    private void selectUnitsBox (double[] rectPoints, boolean isShiftDown) {
        for (SelectableGameElement e : myLevel.getUnits()) {
            if (!isShiftDown)
                e.select(false);
            if (contains(rectPoints, e.getLocation())) {
                e.select(true);
            }
        }
    }

    private void selectUnitsClick (Point2D clickLoc, boolean isShiftDown) {
        for( SelectableGameElement e : myLevel.getUnits()){
            if (!isShiftDown)
                e.select(false);
            double[] bounds = e.getBounds();
            double[] cornerBounds =
                    new double[] { e.getLocation().getX() - bounds[2] / 2,
                                  e.getLocation().getY() - bounds[3] / 2,
                                  e.getLocation().getX() + bounds[2] / 2,
                                  e.getLocation().getY() + bounds[3] / 2 };

            if (contains(cornerBounds, clickLoc)) {
                e.select(true);
                break;
            }
        }
    }
    
    private boolean contains (double[] rectPoints, Point2D unitLocationCenter) {

        double topLeftX = rectPoints[0];
        double topLeftY = rectPoints[1];
        double bottomRightX = rectPoints[2];
        double bottomRightY = rectPoints[3];

        if (topLeftX <= unitLocationCenter.getX()
            && bottomRightX >= unitLocationCenter.getX()) {
            if (topLeftY <= unitLocationCenter.getY()
                && bottomRightY >= unitLocationCenter.getY()) { return true; }
        }
        return false;

    }

    private void sendClickToSelectedUnits (Point2D click, boolean isPrimary,
                                           boolean shiftHeld) {
        for (SelectableGameElement e : myLevel.getUnits().stream()
                .filter(e -> e.isSelected()).collect(Collectors.toList())) {
            if (!isPrimary) {
                if (!shiftHeld)
                    e.clearHeadings();
                e.setHeading(click);
            }
        }
    }

    /**
     * Update the rectangle on the image and check for clicks
     */
    // TODO move this functionality to a more appropriate class
    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof SelectionBox) {
            SelectionBox SB = (SelectionBox) o;
            double[] points = SB.getPoints();
            // ClickManager clickManager = (ClickManager) o;
            selectUnitsBox(points, SB.getLastClick().isShiftDown());
        }
        else if (o instanceof ClickManager) {
            ClickManager clickManager = (ClickManager) o;
            if (clickManager.getLastClick().getButton() == MouseButton.PRIMARY) {
                System.out.println("Click: "+clickManager.getMapLoc().getX()+", "+clickManager.getMapLoc().getY());
                selectUnitsClick(clickManager.getMapLoc(), clickManager.getLastClick()
                        .isShiftDown());
            }
            sendClickToSelectedUnits(clickManager.getMapLoc(), clickManager

                    .getLastClick().getButton().equals(MouseButton.PRIMARY),
                                     clickManager.getLastClick().isShiftDown());

        }
        else if (o instanceof KeyboardManager) {
            System.out.println("Typed: "
                               + ((KeyboardManager) o).getLastCharacter());
        }
    }
}
