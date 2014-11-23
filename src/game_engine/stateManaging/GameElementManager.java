package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.Dimension;
import game_engine.visuals.UI.ClickManager;
import game_engine.visuals.UI.KeyboardManager;
import game_engine.visuals.UI.SelectionBox;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;


/**
 * A manager for selecting, deselecting, and interacting with game elements
 *
 * @author John, Steve
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
     * @param typeName the name of the type of the game elements
     * @return all the game elements with the given type
     */
    public List<GameElementState> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .map(o -> o.getState())
                .collect(Collectors.toList());
    }

    public void addElementToLevel (String typeName) {
        // TODO: add factories
    }

    /**
     * Select all the elements in a given rectangle
     *
     * @param rectPoints the points in the rectangle surrounding the player's units
     */
    private void selectPlayerUnits (double[] rectPoints) {
        for (SelectableGameElement e : myLevel.getUnits()) {
            e.select(false);
            System.out.println("Unit Unselected");
            double[] bounds = e.getBounds();
            // TODO: this doesn't work once the screen has scrolled
            Polygon polygonBounds = new Polygon();
            polygonBounds.getPoints().addAll(new Double[] { bounds[0], bounds[1],
                                                           bounds[0] + bounds[2], bounds[1],
                                                           bounds[0] + bounds[2],
                                                           bounds[1] + bounds[3], bounds[0],
                                                           bounds[1] + bounds[3] });

            if (polygonBounds.intersects(rectPoints[0], rectPoints[1], rectPoints[2]-rectPoints[0], rectPoints[3]-rectPoints[1])){
                e.select(true);
                System.out.println("Selected Unit");
            }
        }
    }

    private void sendClickToSelectedUnits (Point2D click, boolean isPrimary) {
        for (SelectableGameElement e : myLevel.getUnits().stream().filter(e -> e.isSelected())
                .collect(Collectors.toList())) {
            if (!isPrimary) {
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
            double[] points = ((SelectionBox) o).getPoints();
            selectPlayerUnits(points);
        }
        else if (o instanceof ClickManager) {
            Point2D click = ((ClickManager) o).getLoc();
            // TODO implement sending orders to units based on click
            // ((ClickManager) o).isPrimary(), ((ClickManager) o).isSecondary()
            boolean isSecondary;
            if (isSecondary = ((ClickManager) o).isSecondary()) {
                sendClickToSelectedUnits(click, !isSecondary);
            }
            System.out.println("Click: " +
                               (((ClickManager) o).isPrimary() ? "primary" : "secondary") +
                               ", loc: " + click.getX() + ", " + click.getY());

        }
        else if (o instanceof KeyboardManager) {
            System.out.println("Typed: " + ((KeyboardManager) o).getLastCharacter());
        }
    }
}
