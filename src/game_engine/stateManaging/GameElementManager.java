package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.visuals.ClickManager;
import game_engine.visuals.SelectionBox;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
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
            double[] bounds = e.getBounds();
            System.out.println("Unit bounding box:");
            System.out.println("(" + bounds[0] + ", " + bounds[1] + ") , (" +
                    (bounds[0] + bounds[2]) + ", " + (bounds[1] + bounds[3]) + ")");
            Polygon polygonBounds = new Polygon();
            polygonBounds.getPoints().addAll(new Double[] { bounds[0], bounds[1],
                                                            bounds[0] + bounds[2], bounds[1],
                                                            bounds[0] + bounds[2],
                                                            bounds[1] + bounds[3], bounds[0],
                                                            bounds[1] + bounds[3] });

            if (polygonBounds.intersects(rectPoints[0], rectPoints[1], rectPoints[2] -
                                         rectPoints[0],
                                         rectPoints[3] - rectPoints[1])) {
                System.out.println("selected a unit with new system");
                e.select(true);
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
            ((ClickManager) o).getLoc();

        }
    }
}
