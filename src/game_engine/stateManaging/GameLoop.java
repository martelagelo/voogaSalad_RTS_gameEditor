package game_engine.stateManaging;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.computers.boundsComputers.VisionComputer;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.visuals.ScrollableBackground;
import game_engine.visuals.VisualManager;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class GameLoop {

	public static final Double framesPerSecond = 60.0;

	private Level myCurrentLevel;
	private ScrollableBackground myBackground;
	private List<Computer> myComputerList = new ArrayList<Computer>();
	private Timeline timeline;

	private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
		@Override
		public void handle (ActionEvent evt) {
			update();
		}
	};

	public GameLoop (Level level, VisualManager visualManager) {
		myBackground = visualManager.getBackground();
		myCurrentLevel = level;
		// myComputerList.add(new CollisionComputer());
		// myComputerList.add(new VisionComputer());
		timeline = new Timeline();
		start(framesPerSecond);

	}
	
	public void startGameLoop() {
		KeyFrame frame = start(framesPerSecond);
		startTimeline(frame);
	}

	private KeyFrame start (Double framesPerSecond) {
		KeyFrame frame = new KeyFrame(Duration.millis(1000 / framesPerSecond), oneFrame);
		return frame;
	}

	private void update () {
		myBackground.update();
		List<DrawableGameElement> allElements =
				new ArrayList<DrawableGameElement>();
		allElements.addAll(myCurrentLevel.getUnits());
		allElements.addAll(myCurrentLevel.getTerrain());
		for (SelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
			for (Computer<SelectableGameElement, DrawableGameElement> c : myComputerList) {
				c.compute(selectableElement, allElements);
			}
		}

		for (SelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
			selectableElement.updateAnimation();
		}
	}
	
	private void startTimeline(KeyFrame frame) {
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(frame);
		timeline.playFromStart();
	}

	public void play () {
		timeline.play();
	}

	public void pause () {
		timeline.pause();
	}

	public void stop () {
		timeline.stop();
	}

}
