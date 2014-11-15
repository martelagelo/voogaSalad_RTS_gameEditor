package game_engine.stateManaging;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.computers.boundsComputers.VisionComputer;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.RenderedDrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.RenderedLevel;
import game_engine.gameRepresentation.renderedRepresentation.RenderedSelectableGameElement;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class GameLoop {

    private RenderedLevel myCurrentLevel;
    private List<Computer> myComputerList = new ArrayList<Computer>();

    public GameLoop (RenderedLevel level) {
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

    public void update () {
        List<RenderedDrawableGameElement> allElements =
                new ArrayList<RenderedDrawableGameElement>();
        allElements.addAll(myCurrentLevel.getUnits());
        allElements.addAll(myCurrentLevel.getTerrain());
        for (RenderedSelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
            for (Computer<RenderedSelectableGameElement, RenderedDrawableGameElement> c : myComputerList) {
                c.compute(selectableElement, allElements);
            }
        }

        for (RenderedSelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
            // TODO : flag for active
            selectableElement.update();
        }
    }

    public KeyFrame start (Double frameRate) {
        return new KeyFrame(Duration.millis(1000 / 60), oneFrame);
    }

    public void play () {
        // TODO Auto-generated method stub

    }

    public void pause () {
        // TODO Auto-generated method stub

    }

}
