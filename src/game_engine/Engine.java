package game_engine;

import game_engine.gameRepresentation.conditions.evaluators.parameters.ParameterFactory;
import game_engine.gameRepresentation.renderedRepresentation.Game;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.stateRepresentation.GameState;
import game_engine.stateManaging.GameElementManager;
import game_engine.stateManaging.GameLoop;
import game_engine.visuals.VisualManager;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;


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

    public Engine (GameState game, Object saveLoadUtility) {
        // TODO hard-coding the visual representation for now, should remove this dependency
        myGame = new Game(game);
        mySaveLoadUtility = saveLoadUtility;
        myVisualManager = new VisualManager(new Group(), 600, 600);
    }

    public Group getVisualRepresentation () {
        // TODO: figure out what stores our shit
        return myVisualManager.getVisualRepresentation();
    }

    public void selectLevel (String name) {
        myGame.setCurrentLevel(name);
        myGameLoop = new GameLoop(myGame.getCurrentLevel(), myVisualManager);
        myElementManager = new GameElementManager(myGame.getCurrentLevel());
        myVisualManager.addObjects(myGame.getCurrentLevel().getGroup());
        myVisualManager.addBoxObserver(myElementManager);

    }

    public void play () {
        myGameLoop.play();
    }

    public void pause () {
        myGameLoop.pause();
    }

    public void save () {
        // TODO save stuff
        // mySaveLoadUtility.save(myGame);
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

    public Scene getScene () {
        return myVisualManager.getScene();
    }
}
