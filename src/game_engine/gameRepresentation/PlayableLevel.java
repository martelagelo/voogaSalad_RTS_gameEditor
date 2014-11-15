package game_engine.gameRepresentation;

import game_engine.gameRepresentation.gameElement.DrawableGameElement;
import game_engine.gameRepresentation.gameElement.GameElement;
import game_engine.gameRepresentation.gameElement.SelectableGameElement;
import java.util.List;


public class PlayableLevel {

    public String name;
    public String description;
    private boolean isActiveLevel = false;
    private List<RenderedDrawableGameElement> terrain;
    private List<SelectableGameElement> units;
    private List<GameElement> goal;

    public PlayableLevel (Level level) {
        for (DrawableGameElement element : level.getTerrain()) {
            terrain.add(new RenderedDrawableGameElement(element));

        }
    }

    public List<DrawableGameElement> getTerrain () {
        return terrain;
    }

    public List<SelectableGameElement> getUnits () {
        return units;
    }

    public List<GameElement> getGoal () {
        return goal;
    }

}
