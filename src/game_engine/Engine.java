package game_engine;

import game_engine.gameRepresentation.renderedRepresentation.Game;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import java.util.Observer;
import player.VisualManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
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
    private VisualManager myVisualManager;

    public Engine (Game game, Object saveLoadUtility, VisualManager vm) {
        // TODO hard-coding the visual representation for now, should remove this dependency
        myGame = game;
        mySaveLoadUtility = saveLoadUtility;
        myVisualManager = vm;
        
    }

    public Group getVisualRepresentation () {
        // TODO: figure out what stores our shit
        return myVisualManager.getVisualRepresentation();
    }

    public void selectLevel (String name) {
        myGame.setCurrentLevel(name);
        myGameLoop = new GameLoop(myGame.getCurrentLevel());
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
