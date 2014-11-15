package game_engine;

import game_engine.gameRepresentation.Game;
import game_engine.gameRepresentation.renderedRepresentation.RenderedLevel;
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
    private Object mySaveLoadUtility;

    public Engine (Game game, Object saveLoadUtility) {
        myGame = game;
        mySaveLoadUtility = saveLoadUtility;
    }

    public Node getVisualRepresentation () {
        // TODO: figure out what stores our shit
        return;
    }

    public void selectLevel (String name) {
        myGame.setCurrentLevel(name);
        myGameLoop = new GameLoop(new RenderedLevel(myGame.getCurrentLevel()));
        myElementManager = new GameElementManager(myGame.getCurrentLevel());
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
