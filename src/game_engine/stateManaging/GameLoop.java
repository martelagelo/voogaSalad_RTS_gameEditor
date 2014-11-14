package game_engine.stateManaging;

import java.util.ArrayList;
import java.util.List;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.computers.boundsComputers.VisionComputer;
import game_engine.gameRepresentation.GameElement;
import game_engine.gameRepresentation.Level;
import game_engine.gameRepresentation.SelectableGameElement;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameLoop {

	private Level myCurrentLevel;
	private List<GameElement> myAllElements = new ArrayList<GameElement>();
	private List<Computer> myComputerList = new ArrayList<Computer>();

	public GameLoop(Level level) {
		myCurrentLevel = level;
		populateMyAllElements();
		myComputerList.add(new CollisionComputer());
		myComputerList.add(new VisionComputer());
	}

	private void populateMyAllElements() {
		myAllElements.addAll(myCurrentLevel.getUnits());
		myAllElements.addAll(myCurrentLevel.getTerrain());
	}

	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent evt) {
			update();
		}
	};

	private void filterObjects() {

	}

	public void init() {

	}

	/**
	 * Create the game's frame
	 */
	public KeyFrame start(Double frameRate) {
		return new KeyFrame(Duration.millis(1000 / 60), oneFrame);
	}

	public void update() {
		for (Computer c : myComputerList) {
			for (SelectableGameElement GE : myCurrentLevel.getUnits()) {
				if (GE.getIsActive()) {
					c.compute(GE, myAllElements);
				}
			}
		}
	}
}
