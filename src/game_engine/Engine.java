package game_engine;

import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.GameState;
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

    private GameState myGame;
    private GameLoop myGameLoop;
    private GameElementManager myElementManager;
    private Object mySaveLoadUtility;

    public Engine (GameState game, Object saveLoadUtility) {
        myGame = game;
        mySaveLoadUtility = saveLoadUtility;
    }

    public Node getVisualRepresentation () {
        // TODO: figure out what stores our shit
        return;
    }

    public void selectLevel (String name) {
        myGame.setCurrentLevel(name);
        myGameLoop = new GameLoop(new Level(myGame.getCurrentLevel()));
        myElementManager = new GameElementManager(new Level(myGame.getCurrentLevel()));
    }

    public void play () {
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }

    public void save () {
        mySaveLoadUtility.save(myGame);
    }

    public void load (GameState game) {
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
