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
import java.util.stream.Collectors;
import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;


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
            double[] bounds = e.getBounds();
//            System.out.println("Unit bounding box:");
//            System.out.println("("+bounds[0]+", "+bounds[1]+") , (" + (bounds[0]+bounds[2])+", "+(bounds[1]+bounds[3])+")");
            Polygon polygonBounds = new Polygon();
            polygonBounds.getPoints().addAll(new Double[] { bounds[0], bounds[1],
                                                           bounds[0] + bounds[2], bounds[1],
                                                           bounds[0] + bounds[2],
                                                           bounds[1] + bounds[3], bounds[0],
                                                           bounds[1] + bounds[3] });
            
            if (polygonBounds.intersects(rectPoints[0], rectPoints[1], rectPoints[2]-rectPoints[0], rectPoints[3]-rectPoints[1])){
//                System.out.println("selected a unit with new system");
                e.select(true);
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
            System.out.println("Click: "+(((ClickManager) o).isPrimary()?"primary":"secondary")+
                               ", loc: "+click.getX()+", "+click.getY());
                    
        }
        else if( o instanceof KeyboardManager){
            System.out.println("Typed: "+((KeyboardManager) o).getLastCharacter());
        }
    }
}
