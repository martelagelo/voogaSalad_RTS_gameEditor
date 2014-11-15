package game_engine.gameRepresentation.immutableRepresentation;

import game_engine.gameRepresentation.stateRepresentation.LevelState;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


/**
 * This wrapper allows for the Engine to pass an Object to the GUI without exposing internal state
 * we want hidden.
 * 
 * @author Steve
 *
 */
public class ImmutableLevel implements Observable {

    private LevelState myLevel;

    public ImmutableLevel (LevelState level) {
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

    public boolean isActive () {
        return myLevel.isActive();
    }

}
