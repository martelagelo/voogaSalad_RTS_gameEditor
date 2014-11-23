package game_engine.stateManaging;

import game_engine.computers.Computer;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.visuals.ScrollableBackground;
import game_engine.visuals.VisualManager;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


/**
 * The main game loop. Sets the timeline and game loop in motion and calls the update method of all
 * the elements in the game
 *
 * @author Makru Dengu, Steve
 *
 */
public class GameLoop {
    public static final Double framesPerSecond = 60.0;
	private String myCampaignName;
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

    /**
     * Start the game loop
     */
    public void startGameLoop () {
        KeyFrame frame = start(framesPerSecond);
        startTimeline(frame);
    }

    /**
     * Create a keyframe with the given framerate
     *
     * @param framesPerSecond the number of frames per second of the keyframe
     * @return the keyframe
     */
    private KeyFrame start (Double framesPerSecond) {
        KeyFrame frame = new KeyFrame(Duration.millis(1000 / framesPerSecond), oneFrame);
        return frame;
    }
	public GameLoop (String campaignName, Level level, VisualManager visualManager) {
		myBackground = visualManager.getBackground();
		myCampaignName = campaignName;
		myCurrentLevel = level;
		// myComputerList.add(new CollisionComputer());
		// myComputerList.add(new VisionComputer());
		timeline = new Timeline();
		startGameLoop();
	}
	

 
	private void update () {
		//Updates the background of the application
		myBackground.update();
		//Updates all of the conditions and actions of the game elements
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
			selectableElement.update();
		}
	}
	
	private void startTimeline(KeyFrame frame) {
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().clear();
		timeline.getKeyFrames().add(frame);
		timeline.playFromStart();
	}

    /**
     * Play the game
     */
    public void play () {
        timeline.play();
    }

    /**
     * Pause the game
     */
    public void pause () {
        timeline.pause();
    }

    /**
     * Stop the game
     */
    public void stop () {
        timeline.stop();
    }

    /**
     * Indicate if this level is the current levels
     *
     * @param level the levelState of the level in question
     * @return a boolean indicating if the level is the current level
     */        
    public boolean isCurrentLevel (LevelState level, String campaignName) {
        return (level.getName().equals(myCurrentLevel.getName()) && myCampaignName.equals(campaignName));
    }

}
