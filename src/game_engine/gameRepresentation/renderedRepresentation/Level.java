package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.stateRepresentation.LevelState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.DrawableGameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.GameElementState;
import game_engine.gameRepresentation.stateRepresentation.gameElement.SelectableGameElementState;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;


public class Level {

    public String name;
    public String description;
    private LevelState myLevelState;
    private Group myGroup;
    private List<DrawableGameElement> terrain;
    private List<SelectableGameElement> units;
    private List<GameElementState> goals;

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
        goals.addAll(level.getGoal());

        List<DrawableGameElement> allElements = new ArrayList<DrawableGameElement>();
        allElements.addAll(units);
        allElements.addAll(terrain);
        for (DrawableGameElement element : allElements) {
            myGroup.getChildren().add(element.getNode());
        }
    }

    public List<DrawableGameElement> getTerrain () {
        return terrain;
    }

    public List<SelectableGameElement> getUnits () {
        return units;
    }

    public List<GameElementState> getGoal () {
        return goals;
    }

    public boolean isActive () {
        return myLevelState.isActive();
    }

    public void setActive () {
        myLevelState.setActive();
    }

    public Group getGroup () {
        return myGroup;
    }

}
