package game_engine.stateManaging;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import game_engine.visuals.SelectionBox;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;


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
                    System.out.println("selected a unit");
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
    }
}
