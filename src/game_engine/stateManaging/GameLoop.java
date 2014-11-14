package game_engine.stateManaging;

import java.util.List;

import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.computers.boundsComputers.VisionComputer;
import game_engine.gameRepresentation.Game;
import game_engine.gameRepresentation.Level;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class GameLoop {
	
	private Level myCurrentLevel;
	
	public GameLoop(Level level) {
		myCurrentLevel = level;
		
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
    	for (GameElement GE: myCurrentLevel.g)
    }
}
