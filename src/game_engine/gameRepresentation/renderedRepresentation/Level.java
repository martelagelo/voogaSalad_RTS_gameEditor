package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;


/**
 * 
 * @author Jonathan Tseng, Nishad, Rahul
 *
 */
public class Level {

    private LevelState myLevelState;
    private Group myGroup;
    private List<DrawableGameElement> terrain;
    private List<SelectableGameElement> units;
    private List<GameElement> goals;

    public Level (LevelState level) {
        terrain = new ArrayList<>();
        units = new ArrayList<>();
        goals = new ArrayList<>();
        myLevelState = level;
        for (DrawableGameElementState element : level.getTerrain()) {
            terrain.add(new DrawableGameElement(element));
        }
        for (SelectableGameElementState element : level.getUnits()) {
            units.add(new SelectableGameElement(element));
        }
        // TODO Use factory to create game elements from game element states and add to this list
        //goals.addAll(level.getGoals());

        List<DrawableGameElement> allElements = new ArrayList<DrawableGameElement>();
        allElements.addAll(terrain);
        allElements.addAll(units);
        myGroup = new Group();
        for (DrawableGameElement element : allElements) {
            myGroup.getChildren().add(element.getNode());
        }
    }
    
    public LevelState getLevelState() {
        return myLevelState;
    }

    public String getName () {
        return myLevelState.getName();
    }

    public String getDescription () {
        return myLevelState.getDescription();
    }

    public List<DrawableGameElement> getTerrain () {
        return terrain;
    }

    public List<SelectableGameElement> getUnits () {
        return units;
    }

    public List<GameElement> getGoal () {
        return goals;
    }

    public Group getGroup () {
        return myGroup;
    }

}
