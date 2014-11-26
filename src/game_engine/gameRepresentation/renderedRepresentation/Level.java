package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.evaluatables.Evaluatable;
import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.Group;


/**
 * The representation of a basic level in the game. Contains a state of the level, a display group,
 * lists of terrains and units, and a list of goals for the level. Essentially acts as a displayable
 * wrapper around the serializable levelState object.
 * 
 * @author Jonathan, Nishad, Rahul
 *
 */
public class Level {

    private LevelState myLevelState;
    private Group myDisplayGroup;
    private List<DrawableGameElement> myTerrain;
    private List<SelectableGameElement> myUnits;
    private List<GameElement> myGoals;

    /**
     * Take in a level state and create the visual representation of the level from it.
     * 
     * @param levelState
     */
    public Level (LevelState levelState) {
        // TODO split this into multiple methods
        myTerrain = new ArrayList<>();
        myUnits = new ArrayList<>();
        myGoals = new ArrayList<>();
        myLevelState = levelState;
        // Create and add the terrains
        for (DrawableGameElementState element : levelState.getTerrain()) {
            myTerrain.add(new DrawableGameElement(element,
                                                  new HashMap<String, List<Evaluatable<?>>>()));
        }
        // Create and add the units to the map
        for (SelectableGameElementState element : levelState.getUnits()) {
            myUnits.add(new SelectableGameElement(element,
                                                  new HashMap<String, List<Evaluatable<?>>>()));
        }
        // TODO Use factory to create game elements from game element states and add to this list
        // goals.addAll(level.getGoals());

        List<DrawableGameElement> allElements = new ArrayList<DrawableGameElement>();
        allElements.addAll(myTerrain);
        allElements.addAll(myUnits);
        myDisplayGroup = new Group();
        for (DrawableGameElement element : allElements) {
            myDisplayGroup.getChildren().add(element.getNode());
        }
    }

    public LevelState getLevelState () {
        return myLevelState;
    }

    public String getName () {
        return myLevelState.getName();
    }

    public String getDescription () {
        return myLevelState.getDescription();
    }

    public List<DrawableGameElement> getTerrain () {
        return myTerrain;
    }

    public List<SelectableGameElement> getUnits () {
        return myUnits;
    }

    public List<GameElement> getGoals () {
        return myGoals;
    }

    public Group getGroup () {
        return myDisplayGroup;
    }

}
