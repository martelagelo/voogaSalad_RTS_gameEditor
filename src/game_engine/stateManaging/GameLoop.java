package game_engine.stateManaging;

import game_engine.computers.Computer;
import game_engine.computers.boundsComputers.CollisionComputer;
import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.evaluatables.evaluators.AndEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.CollisionEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.Evaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.IfThenEvaluator;
import game_engine.gameRepresentation.evaluatables.evaluators.SubtractionAssignmentEvaluator;
import game_engine.gameRepresentation.evaluatables.parameters.GameElementParameter;
import game_engine.gameRepresentation.evaluatables.parameters.NumericAttributeParameter;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActeeObjectIdentifier;
import game_engine.gameRepresentation.evaluatables.parameters.objectIdentifiers.ActorObjectIdentifier;
import game_engine.gameRepresentation.renderedRepresentation.DrawableGameElement;
import game_engine.gameRepresentation.renderedRepresentation.Level;
import game_engine.gameRepresentation.renderedRepresentation.SelectableGameElement;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.StateTags;
import game_engine.visuals.MiniMap;
import game_engine.visuals.VisualManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Line;
import javafx.util.Duration;


/**
 * The main loop for running the game, checking for collisions, and updating game entities
 * 
 * @author Michael D., John, Steve, Zach
 *
 */
public class GameLoop {

    public static final Double framesPerSecond = 60.0;

    private String myCampaignName;
    private Level myCurrentLevel;
    private VisualManager myVisualManager;
    private MiniMap myMiniMap;

    private List<Computer> myComputers = new ArrayList<>();
    private Timeline timeline;

    private List<Line> unitPaths;

    private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle (ActionEvent evt) {
            update();
        }
    };

    public GameLoop (String campaignName, Level level, VisualManager visualManager, MiniMap miniMap) {
        // myBackground = visualManager.getBackground();
        myVisualManager = visualManager;
        myCampaignName = campaignName;
        myCurrentLevel = level;
        myMiniMap = miniMap;
        unitPaths = new ArrayList<Line>();
        // myComputerList.add(new CollisionComputer());
        myComputers.add(new CollisionComputer());
        // myComputerList.add(new VisionComputer());
        timeline = new Timeline();
        startGameLoop();
        // TODO find a place for this method
        addColisionEvents();
    }

    private void addColisionEvents () {
        Evaluatable<?> objectParameter1 =
                new GameElementParameter(new ActeeObjectIdentifier(), null);
        Evaluatable<?> objectParameter2 =
                new GameElementParameter(new ActorObjectIdentifier(), null);
        Evaluator<?, ?, Boolean> collisionEvaluator =
                new CollisionEvaluator<>(objectParameter1, objectParameter2);
        Evaluatable<?> xPosition =
                new NumericAttributeParameter(StateTags.X_POS_STRING,
                                              null,
                                              new ActorObjectIdentifier());
        Evaluatable<?> yPosition =
                new NumericAttributeParameter(StateTags.Y_POS_STRING, null,
                                              new ActorObjectIdentifier());
        Evaluatable<?> xVelocity = new NumericAttributeParameter(SelectableGameElement.X_VEL, null,
                                                                 new ActorObjectIdentifier());
        Evaluatable<?> yVelocity = new NumericAttributeParameter(SelectableGameElement.Y_VEL, null,
                                                                 new ActorObjectIdentifier());
        Evaluator<?, ?, ?> xAddEvaluator =
                new SubtractionAssignmentEvaluator<>(xPosition, xVelocity);
        Evaluator<?, ?, ?> yAddEvaluator =
                new SubtractionAssignmentEvaluator<>(yPosition, yVelocity);
        Evaluator<?, ?, ?> reverseMotionEvaluator =
                new AndEvaluator<>(xAddEvaluator, yAddEvaluator);
        Evaluator<?, ?, ?> collisionAndStopCAPair =
                new IfThenEvaluator<>(collisionEvaluator, reverseMotionEvaluator);

        myCurrentLevel
                .getUnits()
                .stream()
                .forEach(element -> element.addAction("collision", collisionAndStopCAPair));

    }

    public void startGameLoop () {
        KeyFrame frame = start(framesPerSecond);
        startTimeline(frame);
    }

    private KeyFrame start (Double framesPerSecond) {
        KeyFrame frame = new KeyFrame(Duration.millis(1000 / framesPerSecond), oneFrame);
        return frame;
    }

    private void update () {
        clearLinesFromRoot();
        // Updates the background of the application
        myVisualManager.update();
        // Updates all of the conditions and actions of the game elements
        List<DrawableGameElement> allElements =
                new ArrayList<DrawableGameElement>();
        // TODO add stream that collects into objects
        allElements.addAll(myCurrentLevel.getUnits().stream().map(element -> {
            return (DrawableGameElement) element;
        }).collect(Collectors.toList()));
        // allElements.addAll(myCurrentLevel.getTerrain());
        // TODO fix this logic
        for (SelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
            for (Computer<DrawableGameElement, DrawableGameElement> computer : myComputers) {
                computer.compute(selectableElement, allElements);
            }
        }
        for (SelectableGameElement selectableElement : myCurrentLevel.getUnits()) {
            selectableElement.update();
        }
        myMiniMap.updateMiniMap();
        addPathsToRoot();
    }

    private void addPathsToRoot () {
        for (SelectableGameElement SGE : myCurrentLevel.getUnits()) {
            unitPaths.addAll(SGE.getLines());
        }
        this.myVisualManager.getBackground().getChildren().addAll(unitPaths);
    }

    private void clearLinesFromRoot () {
        this.myVisualManager.getBackground().getChildren().removeAll(unitPaths);
        unitPaths.clear();
    }

    private void startTimeline (KeyFrame frame) {
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

    public boolean isCurrentLevel (LevelState level, String campaignName) {
        return (level.getName().equals(myCurrentLevel.getName()) && myCampaignName
                .equals(campaignName));
    }

}
