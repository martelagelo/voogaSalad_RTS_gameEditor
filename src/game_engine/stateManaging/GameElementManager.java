package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import player.ClickManager;
import player.SelectionBox;


public class GameElementManager implements Observer {

    private Level myLevel;

    public GameElementManager (Level level) {
        myLevel = level;
    }

    public List<GameElementState> findAllElementsOfType (String typeName) {
        return myLevel.getUnits().stream()
                .filter(o -> o.getType().equals(typeName))
                .map(o -> o.getState())
                .collect(Collectors.toList());
    }

    public void addElementToLevel (String typeName) {
        // TODO: add factories
    }

    private void selectPlayerUnits (double[] rectPoints) {
        for (SelectableGameElement e : myLevel.getUnits()) {
            double xLoc =
                    e.getState().getNumericalAttribute(SelectableGameElementState.X_POS_STRING)
                            .doubleValue();
            double yLoc =
                    e.getState().getNumericalAttribute(SelectableGameElementState.Y_POS_STRING)
                            .doubleValue();
            if (xLoc > rectPoints[0] && xLoc < rectPoints[2]) {
                if (yLoc > rectPoints[1] && yLoc < rectPoints[3]) {
                    e.select(true);
                }
            }
        }
    }

    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof SelectionBox) {
            double[] points = ((SelectionBox) o).getPoints();
            selectPlayerUnits(points);
        }
        
        else if (o instanceof ClickManager){
            Point2D click = ((ClickManager) o).getLoc();
            // TODO implement sending orders to units based on click
            // ((ClickManager) o).isPrimary(), ((ClickManager) o).isSecondary()
                    
        }
    }
}
