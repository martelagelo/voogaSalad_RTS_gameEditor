package engine.stateManaging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.state.LevelState;
import model.state.gameelement.StateTags;
import engine.UI.ParticipantManager;
import engine.computers.Computer;
import engine.computers.boundsComputers.CollisionComputer;
import engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import engine.gameRepresentation.renderedRepresentation.Level;
import engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import engine.visuals.VisualManager;


/**
 * The main loop for running the game, checking for collisions, and updating game entities
 *
 * @author Michael D., John L., Steve, Zach
 **/
public class GameLoop extends Observable{
    public static final Double framesPerSecond = 60.0;
    private String myCampaignName;
    private Level myCurrentLevel;
    private GameElementManager myManager;
    private ParticipantManager myParticipantManager;

    private VisualManager myVisualManager;
    private List<Line> unitPaths;

    private List<Computer<DrawableGameElement, DrawableGameElement>> myComputers =
            new ArrayList<>();
    private Timeline timeline;

    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent evt) {
            update();
        }
    };

    public GameLoop (String campaignName,
                     Level level,
                     VisualManager visualManager,
                     GameElementManager elementManager, ParticipantManager participantManager) {
        myVisualManager = visualManager;
        myCampaignName = campaignName;
        myParticipantManager = participantManager;
        myManager = elementManager;
        myCurrentLevel = level;
        unitPaths = new ArrayList<Line>();
        myComputers.add(new CollisionComputer());
        timeline = new Timeline();
        startGameLoop();
    }

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

    /**
     * Update the states of all prominent elements and aspects of the game
     */
    private void update () {
        // Clears all path lines from the GUI
        clearLinesFromRoot();
        // Adds needed path lines to the GUI
         addPathsToRoot();

        // First check for and remove dead units
        Iterator<SelectableGameElement> iter = myCurrentLevel.getUnits().iterator();
        List<SelectableGameElement> elementsToRemove = new ArrayList<>();
        while (iter.hasNext()) {
            SelectableGameElement selectableElement = iter.next();
            if (selectableElement.getNumericalAttribute(StateTags.IS_DEAD).doubleValue() == 1) {
                elementsToRemove.add(selectableElement);
            }
        }
        elementsToRemove.forEach(element -> myCurrentLevel.removeElement(element));

        // Then populate the list of selectable elements for each object
        for (SelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
            for (Computer<DrawableGameElement, DrawableGameElement> computer : myComputers) {
                computer.compute(selectableElement,
                                 myCurrentLevel.getUnits().stream().map(element -> {
                                     return (DrawableGameElement) element;
                                 }).collect(Collectors.toList()));
            }
        }
        // Then update each object
        iter = myCurrentLevel.getUnits().iterator();
        while (iter.hasNext()) {
            SelectableGameElement selectableElement = iter.next();
            selectableElement.update();
        }

        myVisualManager.update(myCurrentLevel.getUnits());
        myParticipantManager.update(myCurrentLevel.getUnits());
        
        //TODO: for testing, remove
        myParticipantManager.adjustParticipantNumericalAttribute(1, "Resources", 0.5);
        
        int levelEndState = myCurrentLevel.evaluateGoals();
        if(levelEndState!=0){
            setChanged();
            this.notifyObservers(levelEndState);
        }
    }

    private void addPathsToRoot () {
        for (SelectableGameElement SGE : myCurrentLevel.getUnits()) {
            // unitPaths.addAll(SGE.getLines());
        }
        myVisualManager.getBackground().getChildren().addAll(unitPaths);
    }

    private void clearLinesFromRoot () {
        myVisualManager.getBackground().getChildren().removeAll(unitPaths);
        unitPaths.clear();
    }

    /**
     * Start the timeline
     *
     * @param frame the keyframe for the timeline
     */
    private void startTimeline (KeyFrame frame) {
        timeline.setCycleCount(Animation.INDEFINITE);
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
        return (level.getName().equals(myCurrentLevel.getName()) && myCampaignName
                .equals(campaignName));
    }
}
