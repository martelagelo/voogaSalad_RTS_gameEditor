package game_engine;

import game_engine.gameRepresentation.Game;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;


/**
 * The concrete boundary of the Engine - this class exposes the public API of the Engine to the rest
 * of the project.
 * 
 * @author Steve
 *
 */
public class Engine implements Observer, Observable {

    private Game myGame;
    private GameLoop myGameLoop;
    private GameElementManager myElementManager;

    public Engine (Game game) {
        myGame = game;
    }

    public Node getVisualRepresentation () {
        // TODO: figure out what stores our shit
        return;
    }

    public void selectLevel (String name) {
        // TODO: ??
    }

    public void play () {
        // TODO Auto-generated method stub

    }

    public void pause () {
        // TODO Auto-generated method stub

    }

    public void save () {
        // TODO Auto-generated method stub

    }

    public void load (Game game) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeListener (InvalidationListener arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update (java.util.Observable arg0, Object arg1) {
        // TODO Auto-generated method stub

    }
}
