package game_engine.stateManaging;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.computers.boundsComputers.VisionComputer;
import game_engine.gameRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.Level;
import game_engine.gameRepresentation.SelectableGameElement;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameLoop {

    private Level myCurrentLevel;
    private List<SelectableGameElement> myActiveElements = new ArrayList<SelectableGameElement>();
    private List<Computer> myComputerList = new ArrayList<Computer>();

    public GameLoop (Level level) {
        myCurrentLevel = level;
        myComputerList.add(new CollisionComputer());
        myComputerList.add(new VisionComputer());
    }

    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent evt) {
            update();
        }
    };

    private void filterObjects () {

    }

    public void init () {

    }

    /**
     * Create the game's frame
     */
    public KeyFrame start (Double frameRate) {
        return new KeyFrame(Duration.millis(1000 / 60), oneFrame);
    }

    public void update () {
        List<DrawableGameElement> allElements = new ArrayList<DrawableGameElement>();
        allElements.addAll(myCurrentLevel.getUnits());
        allElements.addAll(myCurrentLevel.getTerrain());
        for (SelectableGameElement selectableElement : myActiveElements) {
            for (Computer<SelectableGameElement, DrawableGameElement> c : myComputerList) {
                c.compute(selectableElement, allElements);
            }
        }
        
        for (SelectableGameElement selectableElement : myActiveElements) { 
            selectableElement.update();
        }
    }

}
