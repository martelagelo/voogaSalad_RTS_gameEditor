package game_engine.gameRepresentation;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

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
