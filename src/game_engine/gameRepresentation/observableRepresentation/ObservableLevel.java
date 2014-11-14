package game_engine.gameRepresentation.observableRepresentation;

import game_engine.gameRepresentation.Level;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * This wrapper allows for the Engine to pass an Object to the GUI without exposing internal state
 * we want hidden.
 * 
 * @author Steve
 *
 */
public class ObservableLevel implements Observable {

    private Level myLevel;

    public ObservableLevel (Level level) {
        myLevel = level;
    }

    @Override
    public void addListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

}
