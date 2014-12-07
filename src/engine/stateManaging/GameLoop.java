package engine.stateManaging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
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
public class GameLoop extends Observable {
    public static final Double framesPerSecond = 60.0;
    private Level myCurrentLevel;
    private GameElementManager myManager;
    private ParticipantManager myParticipantManager;

    private VisualManager myVisualManager;

    private List<Computer<DrawableGameElement, DrawableGameElement>> myComputers =
            new ArrayList<>();
    private Timeline timeline;

    private EventHandler<ActionEvent> oneFrameRunner = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent evt) {
            updateRunner();
        }
    };

    private EventHandler<ActionEvent> oneFrameEditor = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent evt) {
            updateEditor();
        }
    };

    public GameLoop (Level level,
                     VisualManager visualManager,
                     GameElementManager elementManager, ParticipantManager participantManager) {
        myVisualManager = visualManager;
        myParticipantManager = participantManager;
        myManager = elementManager;
        myCurrentLevel = level;
        myComputers.add(new CollisionComputer());
        timeline = new Timeline();
        startTimeline();
    }
    
    /**
     * Update the states of all prominent elements and aspects of the game
     */
    private void updateRunner () {
    	//For waypoint creation. Can Delete if we dont want the capability
        //myVisualManager.drawWayPointLines(this.myCurrentLevel.getUnits());

        // First check for and remove dead units
        Iterator<SelectableGameElement> iter = myCurrentLevel.getUnits().iterator();
        List<SelectableGameElement> elementsToRemove = new ArrayList<>();
        while (iter.hasNext()) {
            SelectableGameElement selectableElement = iter.next();
            if (selectableElement.getNumericalAttribute(StateTags.IS_DEAD.getValue()).doubleValue() == 1) {
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

        // TODO: for testing, remove

//        myParticipantManager.adjustParticipantNumericalAttribute("BLUE", StateTags.RESOURCES.getValue(), 0.5);
//        myParticipantManager.adjustParticipantNumericalAttribute(1, StateTags.RESOURCES, 0.5);

        int levelEndState = myCurrentLevel.evaluateGoals();
        if (levelEndState != 0) {
            setChanged();
            this.notifyObservers(levelEndState);
        }
    }
    
    /**
     * Update the states of all prominent elements and aspects of the game
     */
    private void updateEditor () {
        Iterator<SelectableGameElement> iter = myCurrentLevel.getUnits().iterator();
        while (iter.hasNext()) {
            SelectableGameElement selectableElement = iter.next();
            selectableElement.update();
        }
        Iterator<DrawableGameElement> terrainIterator = myCurrentLevel.getTerrain().iterator();
        while (terrainIterator.hasNext()) {
            DrawableGameElement drawableElement = terrainIterator.next();
            drawableElement.update();
        }
        myVisualManager.update(myCurrentLevel.getUnits());
    }

    /**
     * Start the timeline
     *
     * @param frame the keyframe for the timeline
     */
    private void startTimeline () {
        timeline.setCycleCount(Animation.INDEFINITE);
        setRunnerLoop();
        timeline.playFromStart();
    }

    public void setEditorLoop() {
        setLoop(new KeyFrame(Duration.millis(1000 / framesPerSecond), oneFrameEditor));
    }
    
    public void setRunnerLoop() {
        setLoop(new KeyFrame(Duration.millis(1000 / framesPerSecond), oneFrameRunner));
    }
    
    private void setLoop(KeyFrame frame) {
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    
    /**
     * Play the game
     */
    public void play () {
        timeline.play();
    }

    /**
     * Toggle Pause the game
     */
    public void pause () {
        if (timeline.getStatus().equals(Status.PAUSED)) {
            timeline.play();
        }
        else {
            timeline.pause();
        }
    }

    /**
     * Stop the game
     */
    public void stop () {
        timeline.stop();
    }

}
