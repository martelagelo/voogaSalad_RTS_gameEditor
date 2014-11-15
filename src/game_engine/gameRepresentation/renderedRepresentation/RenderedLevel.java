package game_engine.gameRepresentation.renderedRepresentation;

import game_engine.gameRepresentation.Level;
import game_engine.gameRepresentation.gameElement.DrawableGameElement;
import game_engine.gameRepresentation.gameElement.GameElement;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;
import java.util.List;


public class RenderedLevel {

    public String name;
    public String description;
    private boolean isActiveLevel = false;
    private List<RenderedDrawableGameElement> terrain;
    private List<RenderedSelectableGameElement> units;
    private List<GameElement> goals;

    public RenderedLevel (Level level) {
        for (DrawableGameElement element : level.getTerrain()) {
            terrain.add(new RenderedDrawableGameElement(element));
        }
        for (SelectableGameElement element : level.getUnits()) {
            units.add(new RenderedSelectableGameElement(element));
        }
        goals.addAll(level.getGoal());
    }

    public List<RenderedDrawableGameElement> getTerrain () {
        return terrain;
    }

    public List<RenderedSelectableGameElement> getUnits () {
        return units;
    }

    public List<GameElement> getGoal () {
        return goals;
    }

}
