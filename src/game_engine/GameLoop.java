package game_engine;

import game_engine.computers.CollisionComputer;
import game_engine.computers.VisionComputer;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameLoop {
	
	
	
	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			update();
		}
	};
	
	private void filterObjects() {
		
	}
	
	public void init() {
		CollisionComputer collisionComputer = new CollisionComputer();
		VisionComputer visionComputer = new VisionComputer();
	}
	
	/**
	 * Create the game's frame
	 */
	public KeyFrame start(Double frameRate) {
		return new KeyFrame(Duration.millis(1000 / 60), oneFrame);
	}
	
	public void update() {
		
	}
}
